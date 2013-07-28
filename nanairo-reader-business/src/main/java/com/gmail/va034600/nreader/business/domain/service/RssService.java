package com.gmail.va034600.nreader.business.domain.service;

import com.gmail.va034600.nreader.business.domain.bean.Subscription;

public interface RssService {
	void loadSubscriptionList();

	void loadArticleList(long subscriptionId);

	int storeArticles();

	int storeArticle(long subscriptionId);

	boolean addSubscription(String url);

	void kidoku(long articleId);

	void kidokuAll(long subscriptionId);

	void deleteSubscription(Subscription subscription);

	boolean loadArticleListByNext(long subscriptionId);
}