package eu.nanairo_reader.business.service;

public interface SubscriptionArticleService {
	void deleteTheOld(long subscriptionId, int count);

	void addSubscriptionArticle(long subscriptionId, long articleId);

	void updateKidokuBySubscriptionId(long subscriptionId);

	void deleteBySubscriptionId(long subscriptionId);

	long findSubscriptionIdByArticleId(long articleId);
}
