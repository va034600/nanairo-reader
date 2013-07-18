package eu.nanairo_reader.business.service;

import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.util.NanairoDateUtils;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.business.vo.FeedResult;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.entity.ArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.ui.NanairoApplication;

public class RssServiceImpl implements RssService {
	@Inject
	NanairoApplication nanairoApplication;

	@Inject
	SubscriptionDao subscriptionDao;

	@Inject
	ArticleDao articleDao;

	@Inject
	SubscriptionArticleDao subscriptionArticleDao;

	@Inject
	RssParsingService rssParsingService;

	@Inject
	SubscriptionListManager subscriptionListManager;

	@Inject
	ArticleListManager articleListManager;

	@Override
	public List<Subscription> loadSubscriptionList() {
		List<SubscriptionEntity> entityList = this.subscriptionDao.findList(null);
		addAll(entityList);
		return this.subscriptionListManager.getSubscriptionList();
	}

	protected void addAll(List<SubscriptionEntity> entityList) {
		this.subscriptionListManager.getSubscriptionList().clear();

		for (SubscriptionEntity entity : entityList) {
			Subscription subscription = convertEntity(entity);
			this.subscriptionListManager.getSubscriptionList().add(subscription);
		}
	}

	private Subscription convertEntity(SubscriptionEntity entity) {
		Subscription subscription = new Subscription();

		subscription.setId(entity.getId());
		subscription.setTitle(entity.getTitle());
		subscription.setUrl(entity.getUrl());
		//TODO リファクタリング
		int midokuCount = this.articleDao.getMidokuCount(entity.getId());
		subscription.setMidokuCount(midokuCount);
		return subscription;
	}

	@Override
	public List<Article> loadArticleList(long subscriptionId) {
		List<Article> list = getArticleList(subscriptionId);
		this.articleListManager.getArticleList().clear();
		this.articleListManager.getArticleList().addAll(list);
		return this.articleListManager.getArticleList();
	}

	private List<Article> getArticleList(long subscriptionId) {
		List<Article> result = new ArrayList<Article>();
		for (ArticleEntity entity : this.articleDao.getListBySubscriptionId(subscriptionId)) {
			Article article = new Article();

			article.setId(entity.getId());
			article.setTitle(entity.getTitle());
			article.setContent(entity.getContent());
			article.setLink(entity.getLink());
			article.setPublishedDate(entity.getPublishedDate());
			article.setMidoku(entity.getMidoku());

			result.add(article);
		}
		return result;
	}

	@Override
	public void storeArticles() {
		for (SubscriptionEntity subscriptionEntity : this.subscriptionDao.findList(null)) {
			storeArticle(subscriptionEntity);
		}

		loadSubscriptionList();
	}

	@Override
	public int storeArticle(long subscriptionId) {
		SubscriptionEntity subscriptionEntity = this.subscriptionDao.findByPrimaryKey(subscriptionId);
		storeArticle(subscriptionEntity);

		// TODO just one
		loadSubscriptionList();

		// TODO 更新件数
		return 0;
	}

	protected void storeArticle(SubscriptionEntity subscriptionEntity) {
		FeedResult feed;
		try {
			feed = this.rssParsingService.getFeedResult(subscriptionEntity.getUrl());
		} catch (RssParsingException e) {
			// TODO ログ出力
			return;
		}

		long subscriptionId = subscriptionEntity.getId();
		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			// 記事一覧追加
			addArticleListByFeed(subscriptionId, feed);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}
	}

	protected void addArticleListByFeed(long subscriptionId, FeedResult feedResult) {
		for (FeedItem feedItem : feedResult.getFeedItemList()) {
			// 登録済みの場合、登録しない。
			boolean flag = isDuplicated(feedItem.getLink());
			if (flag) {
				continue;
			}

			// 記事を登録する。
			long articleId = addArticle(feedItem);

			// 購読記事を登録する。
			addSubscriptionArticle(subscriptionId, articleId);
		}

		// TODO 件数確認
		// 古いのを削除する。
		final int MAX_ARTICLE = 100;
		this.articleDao.deleteTheOld(subscriptionId, MAX_ARTICLE);
		this.subscriptionArticleDao.deleteTheOld(subscriptionId, MAX_ARTICLE);

		List<Article> list = getArticleList(subscriptionId);
		this.articleListManager.getArticleList().clear();
		this.articleListManager.getArticleList().addAll(list);
	}

	protected boolean isDuplicated(String uri) {
		ArticleEntity articleEntityParameter = new ArticleEntity();
		articleEntityParameter.setLink(uri);
		List<ArticleEntity> articleEntitieList = this.articleDao.findList(articleEntityParameter);
		if (articleEntitieList.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	protected long addArticle(FeedItem feedItem) {
		ArticleEntity articleEntity = new ArticleEntity();

		articleEntity.setTitle(feedItem.getTitle());
		articleEntity.setContent(feedItem.getContent());
		articleEntity.setLink(feedItem.getLink());
		articleEntity.setPublishedDate(NanairoDateUtils.convertDateToString(feedItem.getPublishedDate()));
		articleEntity.setMidoku(MIDOKU_ON);

		long articleId = this.articleDao.add(articleEntity);

		return articleId;
	}

	protected void addSubscriptionArticle(long subscriptionId, long articleId) {
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		subscriptionArticleEntity.setArticleId(articleId);
		this.subscriptionArticleDao.add(subscriptionArticleEntity);
	}

	@Override
	public boolean addSubscription(String url) {
		FeedResult feedResult;
		try {
			feedResult = this.rssParsingService.getFeedResult(url);
		} catch (RssParsingException e) {
			// TODO ログ出力
			// feedを取得できない場合、登録しない。
			return false;
		}

		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			addSubscription(url, feedResult);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}

		return true;
	}

	protected void addSubscription(String url, FeedResult feedResult) {
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setUrl(url);
		subscriptionEntity.setTitle(feedResult.getTitle());
		long subscriptionId = this.subscriptionDao.add(subscriptionEntity);
		subscriptionEntity.setId(subscriptionId);

		// 記事一覧追加
		addArticleListByFeed(subscriptionId, feedResult);

		// list 追加
		Subscription subscription = convertEntity(subscriptionEntity);
		this.subscriptionListManager.getSubscriptionList().add(subscription);
	}

	@Override
	public void kidoku(long articleId) {
		ArticleEntity articleEntity = this.articleDao.findByPrimaryKey(articleId);
		if (articleEntity.getMidoku() == MIDOKU_OFF) {
			return;
		}

		articleEntity.setMidoku(MIDOKU_OFF);
		this.articleDao.update(articleEntity);

		SubscriptionArticleEntity parameter = new SubscriptionArticleEntity();
		parameter.setArticleId(articleId);
		List<SubscriptionArticleEntity> subscriptionArticleEntitieList = this.subscriptionArticleDao.findList(parameter);

		long subscriptionId = subscriptionArticleEntitieList.get(0).getSubscriptionId();
		for (Subscription subscription : this.subscriptionListManager.getSubscriptionList()) {
			if (subscription.getId() == subscriptionId) {
				subscription.setMidokuCount(subscription.getMidokuCount() - 1);
				break;
			}
		}

		for (Article article : this.articleListManager.getArticleList()) {
			if (article.getId() == articleId) {
				article.setMidoku(MIDOKU_OFF);
				break;
			}
		}
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.subscriptionArticleDao.updateKidokuBySubscriptionId(subscriptionId);

		for (Article article : this.articleListManager.getArticleList()) {
			article.setMidoku(MIDOKU_OFF);
		}

		for (Subscription subscription : this.subscriptionListManager.getSubscriptionList()) {
			if (subscription.getId() == subscriptionId) {
				subscription.setMidokuCount(0);
				break;
			}
		}
	}

	@Override
	public void deleteSubscription(Subscription subscription) {
		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			deleteSubscriptionCore(subscription);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}
	}

	private void deleteSubscriptionCore(Subscription subscription) {
		long subscriptionId = subscription.getId();

		// subscription
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setId(subscriptionId);
		this.subscriptionDao.delete(subscriptionEntity);

		// article
		this.articleDao.deleteBySucriptionId(subscriptionId);

		// subscriptionArticle
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		this.subscriptionArticleDao.delete(subscriptionArticleEntity);

		this.subscriptionListManager.getSubscriptionList().remove(subscription);
	}
}
