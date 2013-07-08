package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.bean.FeedItem;
import eu.nanairo_reader.bean.FeedResult;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.entity.ArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class RssServiceImpl implements RssService {
	@Inject
	SubscriptionDao subscriptionDao;

	@Inject
	ArticleDao articleDao;

	@Inject
	SubscriptionArticleDao subscriptionArticleDao;

	@Inject
	RssParsingService rssParsingService;

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
		for (SubscriptionEntity subscriptionEntity : this.subscriptionDao.getList()) {
			FeedResult feed;
			try {
				feed = this.rssParsingService.getFeedResult(subscriptionEntity.getUrl());
			} catch (RssParsingException e) {
				// TODO ログ出力
				return;
			}

			for (FeedItem feedItem : feed.getFeedItemList()) {
				// 登録済みの場合、登録しない。
				boolean flag = isDuplicated(feedItem.getUri());
				if (flag) {
					continue;
				}

				// 購読記事を登録する。
				long articleId = addArticle(feedItem);

				// 記事を登録する。
				addSubscriptionArticle(subscriptionEntity.getId(), articleId);
			}
		}
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
		articleEntity.setLink(feedItem.getUri());
		// TODO マジックNo
		articleEntity.setMidoku(1);

		long articleId = this.articleDao.add(articleEntity);

		return articleId;
	}

	protected SubscriptionArticleEntity addSubscriptionArticle(long subscriptionId, long articleId) {
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		subscriptionArticleEntity.setArticleId(articleId);
		this.subscriptionArticleDao.add(subscriptionArticleEntity);
		return subscriptionArticleEntity;
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

		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setUrl(url);
		subscriptionEntity.setTitle(feedResult.getTitle());

		this.subscriptionDao.add(subscriptionEntity);

		return true;
	}
}
