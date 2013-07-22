package eu.nanairo_reader.business.service;

import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.business.vo.FeedResult;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dto.SubscriptionDto;
import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.ui.NanairoApplication;

public class RssServiceImpl implements RssService {
	@Inject
	NanairoApplication nanairoApplication;

	@Inject
	SubscriptionDao subscriptionDao;

	@Inject
	ArticleService articleService;

	@Inject
	SubscriptionArticleDao subscriptionArticleDao;

	@Inject
	RssParsingService rssParsingService;

	@Inject
	SubscriptionListManager subscriptionListManager;

	@Override
	public void loadSubscriptionList() {
		List<SubscriptionDto> dtoList = this.subscriptionDao.findListAndMidokuCount();
		this.subscriptionListManager.clear();

		for (SubscriptionDto dto : dtoList) {
			Subscription subscription = convertDto(dto);
			this.subscriptionListManager.add(subscription);
		}
	}

	private Subscription convertDto(SubscriptionDto dto) {
		Subscription subscription = new Subscription();

		subscription.setId(dto.getId());
		subscription.setTitle(dto.getTitle());
		subscription.setUrl(dto.getUrl());
		subscription.setMidokuCount(dto.getMidokuCount());

		return subscription;
	}

	@Override
	public void loadArticleList(long subscriptionId) {
		this.articleService.loadArticleList(subscriptionId);
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
			boolean flag = this.articleService.isDuplicated(feedItem.getLink());
			if (flag) {
				continue;
			}

			// 記事を登録する。
			long articleId = this.articleService.addArticle(feedItem);

			// 購読記事を登録する。
			addSubscriptionArticle(subscriptionId, articleId);
		}

		// TODO 件数確認
		// 古いのを削除する。
		final int MAX_ARTICLE = 100;
		this.articleService.deleteTheOld(subscriptionId, MAX_ARTICLE);
		this.subscriptionArticleDao.deleteTheOld(subscriptionId, MAX_ARTICLE);

		loadArticleList(subscriptionId);
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
		this.subscriptionListManager.add(subscription);
	}

	private Subscription convertEntity(SubscriptionEntity entity) {
		Subscription subscription = new Subscription();

		subscription.setId(entity.getId());
		subscription.setTitle(entity.getTitle());
		subscription.setUrl(entity.getUrl());
		// TODO リファクタリング
		int midokuCount = this.articleService.getMidokuCount(entity.getId());
		subscription.setMidokuCount(midokuCount);
		return subscription;
	}

	@Override
	public void kidoku(long articleId) {
		if (this.articleService.kidoku(articleId)) {
			return;
		}

		SubscriptionArticleEntity parameter = new SubscriptionArticleEntity();
		parameter.setArticleId(articleId);
		List<SubscriptionArticleEntity> subscriptionArticleEntitieList = this.subscriptionArticleDao.findList(parameter);

		long subscriptionId = subscriptionArticleEntitieList.get(0).getSubscriptionId();

		this.subscriptionListManager.kidoku(subscriptionId);
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.subscriptionArticleDao.updateKidokuBySubscriptionId(subscriptionId);

		this.articleService.kidokuAll(subscriptionId);

		this.subscriptionListManager.kidokuAll(subscriptionId);
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
		this.articleService.deleteBySucriptionId(subscriptionId);

		// subscriptionArticle
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		this.subscriptionArticleDao.delete(subscriptionArticleEntity);

		this.subscriptionListManager.remove(subscription);
	}
}
