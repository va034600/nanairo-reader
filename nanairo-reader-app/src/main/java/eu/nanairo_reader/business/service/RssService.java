package eu.nanairo_reader.business.service;

import eu.nanairo_reader.business.bean.Subscription;

public interface RssService {
	void loadSubscriptionList();

	void loadArticleList(long subscriptionId);

	int storeArticles();

	int storeArticle(long subscriptionId);

	boolean addSubscription(String url);

	void kidoku(long articleId);

	void kidokuAll(long subscriptionId);

	void deleteSubscription(Subscription subscription);
}