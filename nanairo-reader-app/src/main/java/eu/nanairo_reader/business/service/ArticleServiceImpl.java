package eu.nanairo_reader.business.service;

import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.util.NanairoDateUtils;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.entity.ArticleEntity;

public class ArticleServiceImpl implements ArticleService{
	@Inject
	ArticleDao articleDao;

	@Inject
	ArticleListManager articleListManager;

	public int getMidokuCount(Long subscriptionId) {
		return this.articleDao.getMidokuCount(subscriptionId);
	}

	public void loadArticleList(long subscriptionId) {
		this.articleListManager.clear();

		List<ArticleEntity> entityList = this.articleDao.getListBySubscriptionId(subscriptionId);
		for (ArticleEntity entity : entityList) {
			Article article = convertEntity(entity);
			this.articleListManager.add(article);
		}
	}

	private Article convertEntity(ArticleEntity entity) {
		Article article = new Article();

		article.setId(entity.getId());
		article.setTitle(entity.getTitle());
		article.setContent(entity.getContent());
		article.setLink(entity.getLink());
		article.setPublishedDate(entity.getPublishedDate());
		article.setMidoku(entity.getMidoku());

		return article;
	}

	public boolean isDuplicated(String uri) {
		ArticleEntity articleEntityParameter = new ArticleEntity();
		articleEntityParameter.setLink(uri);
		List<ArticleEntity> articleEntitieList = this.articleDao.findList(articleEntityParameter);
		if (articleEntitieList.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public long addArticle(FeedItem feedItem) {
		ArticleEntity articleEntity = new ArticleEntity();

		articleEntity.setTitle(feedItem.getTitle());
		articleEntity.setContent(feedItem.getContent());
		articleEntity.setLink(feedItem.getLink());
		articleEntity.setPublishedDate(NanairoDateUtils.convertDateToString(feedItem.getPublishedDate()));
		articleEntity.setMidoku(MIDOKU_ON);

		long articleId = this.articleDao.add(articleEntity);

		return articleId;
	}

	public void deleteBySucriptionId(long subscriptionId) {
		this.articleDao.deleteBySucriptionId(subscriptionId);
	}

	public void deleteTheOld(long subscriptionId, int count) {
		this.articleDao.deleteTheOld(subscriptionId, count);
	}

	public void kidokuAll(long subscriptionId) {
		this.articleListManager.kidokuAll(subscriptionId);
	}

	public boolean kidoku(long articleId) {
		ArticleEntity articleEntity = this.articleDao.findByPrimaryKey(articleId);
		if (articleEntity.getMidoku() == MIDOKU_OFF) {
			return true;
		}

		articleEntity.setMidoku(MIDOKU_OFF);
		this.articleDao.update(articleEntity);
		this.articleListManager.kidoku(articleId);

		return false;
	}
}
