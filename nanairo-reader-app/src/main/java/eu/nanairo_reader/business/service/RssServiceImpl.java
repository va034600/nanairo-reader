package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.bean.FeedItem;
import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.entity.ArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

public class RssServiceImpl implements RssService {
	@Inject
	private SubscriptionDao subscriptionDao;

	@Inject
	private ArticleDao articleDao;

	@Inject
	private SubscriptionArticleDao subscriptionArticleDao;

	@Inject
	private RssParsingService rssParsingService;

	public List<Subscription> getSubscriptionList() {
		List<Subscription> result = new ArrayList<Subscription>();
		for (SubscriptionEntity entity : this.subscriptionDao.getList()) {
			Subscription subscription = new Subscription();

			subscription.setId(entity.getId());
			subscription.setTitle(entity.getTitle());
			subscription.setUrl(entity.getUrl());
			int midokuCount = this.articleDao.getMidokuCount(entity.getId());
			subscription.setMidokuCount(midokuCount);

			result.add(subscription);
		}
		return result;
	}

	@Override
	public List<Article> getArticleList(long id) {
		List<Article> result = new ArrayList<Article>();
		for (ArticleEntity entity : this.articleDao.getList(id)) {
			Article article = new Article();

			article.setTitle(entity.getTitle());
			article.setContent(entity.getContent());
			article.setLink(entity.getLink());

			result.add(article);
		}
		return result;
	}

	@Override
	public void storeArticles() {
		for (SubscriptionEntity entity : this.subscriptionDao.getList()) {
			List<FeedItem> feedItemList = this.rssParsingService.getArticleList(entity.getUrl());
			for (FeedItem feedItem : feedItemList) {
				ArticleEntity articleEntity = new ArticleEntity();

				articleEntity.setTitle(feedItem.getTitle());
				articleEntity.setContent(feedItem.getContent());
				articleEntity.setLink(feedItem.getUri());
				//TODO マジックNo
				articleEntity.setMidoku(1);
				// TODO

				long articleId = this.articleDao.add(articleEntity);

				// TODO
				SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
				subscriptionArticleEntity.setSubscriptionId(entity.getId());
				subscriptionArticleEntity.setArticleId(articleId);
				this.subscriptionArticleDao.add(subscriptionArticleEntity);
			}
		}
	}
}
