package eu.nanairo_reader.business.service;

import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.util.NanairoDateUtils;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.business.vo.FeedResult;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.entity.ArticleEntity;

public class ArticleServiceImpl implements ArticleService {
	@Inject
	ArticleDao articleDao;

	@Inject
	ArticleListManager articleListManager;

	@Inject
	SubscriptionArticleService subscriptionArticleService;

	@Override
	public void loadArticleList(long subscriptionId) {
		this.articleListManager.clear();

		List<ArticleEntity> entityList = this.articleDao.getListBySubscriptionId(subscriptionId);
		for (ArticleEntity entity : entityList) {
			Article article = convertEntity(entity);
			this.articleListManager.add(article);
		}
	}

	protected Article convertEntity(ArticleEntity entity) {
		Article article = new Article();

		article.setId(entity.getId());
		article.setTitle(entity.getTitle());
		article.setContent(entity.getContent());
		article.setLink(entity.getLink());
		article.setPublishedDate(entity.getPublishedDate());
		article.setMidoku(entity.getMidoku());

		return article;
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

	protected long addArticle(long subscriptionId, FeedItem feedItem) {
		ArticleEntity articleEntity = new ArticleEntity();

		articleEntity.setTitle(feedItem.getTitle());
		articleEntity.setContent(feedItem.getContent());
		articleEntity.setLink(feedItem.getLink());
		articleEntity.setPublishedDate(NanairoDateUtils.convertDateToString(feedItem.getPublishedDate()));
		articleEntity.setMidoku(MIDOKU_ON);

		long articleId = this.articleDao.add(articleEntity);

		// 購読記事を登録する。
		this.subscriptionArticleService.addSubscriptionArticle(subscriptionId, articleId);

		return articleId;
	}

	@Override
	public void deleteBySucriptionId(long subscriptionId) {
		this.articleDao.deleteBySucriptionId(subscriptionId);
		this.subscriptionArticleService.deleteBySubscriptionId(subscriptionId);
	}

	protected void deleteTheOld(long subscriptionId, int count) {
		this.articleDao.deleteTheOld(subscriptionId, count);
		this.subscriptionArticleService.deleteTheOld(subscriptionId, count);
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.subscriptionArticleService.updateKidokuBySubscriptionId(subscriptionId);
		this.articleListManager.kidokuAll(subscriptionId);
	}

	@Override
	public Long kidoku(long articleId) {
		ArticleEntity articleEntity = this.articleDao.findByPrimaryKey(articleId);
		if (articleEntity.getMidoku() == MIDOKU_OFF) {
			return null;
		}

		articleEntity.setMidoku(MIDOKU_OFF);
		this.articleDao.update(articleEntity);
		this.articleListManager.kidoku(articleId);

		long subscriptionId = this.subscriptionArticleService.findSubscriptionIdByArticleId(articleId);

		return subscriptionId;
	}

	@Override
	public int addArticleListByFeed(long subscriptionId, FeedResult feedResult) {
		for (FeedItem feedItem : feedResult.getFeedItemList()) {
			// 登録済みの場合、登録しない。
			boolean flag = isDuplicated(feedItem.getLink());
			if (flag) {
				continue;
			}

			// 記事を登録する。
			addArticle(subscriptionId, feedItem);
		}

		// TODO 件数確認
		// 古いのを削除する。
		final int MAX_ARTICLE = 100;
		deleteTheOld(subscriptionId, MAX_ARTICLE);

		loadArticleList(subscriptionId);

		int midokuCount = this.articleDao.getMidokuCount(subscriptionId);

		return midokuCount;
	}

}
