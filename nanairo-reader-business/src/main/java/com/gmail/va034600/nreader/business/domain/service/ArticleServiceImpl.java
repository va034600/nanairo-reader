package com.gmail.va034600.nreader.business.domain.service;

import static com.gmail.va034600.nreader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import static com.gmail.va034600.nreader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import java.util.List;

import javax.inject.Inject;

import com.gmail.va034600.nreader.business.domain.bean.Article;
import com.gmail.va034600.nreader.business.rss.vo.FeedItem;
import com.gmail.va034600.nreader.business.rss.vo.FeedResult;
import com.gmail.va034600.nreader.business.util.NanairoDateUtils;
import com.gmail.va034600.nreader.data.dao.ArticleDao;
import com.gmail.va034600.nreader.data.entity.ArticleEntity;


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

	protected Article addArticle(long subscriptionId, FeedItem feedItem) {
		ArticleEntity articleEntity = new ArticleEntity();

		articleEntity.setTitle(feedItem.getTitle());
		articleEntity.setContent(feedItem.getContent());
		articleEntity.setLink(feedItem.getLink());
		articleEntity.setPublishedDate(NanairoDateUtils.convertDateToString(feedItem.getPublishedDate()));
		articleEntity.setMidoku(MIDOKU_ON);

		long articleId = this.articleDao.add(articleEntity);
		articleEntity.setId(articleId);

		// 購読記事を登録する。
		this.subscriptionArticleService.addSubscriptionArticle(subscriptionId, articleId);

		Article article = convertEntity(articleEntity);

		return article;
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
		int newMidokuCount = 0;
		for (FeedItem feedItem : feedResult.getFeedItemList()) {
			// 登録済みの場合、登録しない。
			boolean flag = isDuplicated(feedItem.getLink());
			if (flag) {
				continue;
			}

			// 記事を登録する。
			//TODO loadArticleList を削除。代わりに。articleListManagerを操作
			Article article = addArticle(subscriptionId, feedItem);
			newMidokuCount++;
		}

		// TODO 件数確認
		// 古いのを削除する。
		final int MAX_ARTICLE = 100;
		deleteTheOld(subscriptionId, MAX_ARTICLE);

		//TODO loadArticleList を削除。代わりに。articleListManagerを操作
		loadArticleList(subscriptionId);

		return newMidokuCount;
	}

}
