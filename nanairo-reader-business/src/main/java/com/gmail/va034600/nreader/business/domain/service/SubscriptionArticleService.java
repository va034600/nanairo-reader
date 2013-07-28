package com.gmail.va034600.nreader.business.domain.service;

public interface SubscriptionArticleService {
	void deleteTheOld(long subscriptionId, int count);

	void addSubscriptionArticle(long subscriptionId, long articleId);

	void updateKidokuBySubscriptionId(long subscriptionId);

	void deleteBySubscriptionId(long subscriptionId);

	long findSubscriptionIdByArticleId(long articleId);
}
