package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

public interface SubscriptionArticleDao extends BaseDao<SubscriptionArticleEntity, SubscriptionArticleEntity> {
	void deleteTheOld(Long subscriptionId, int count);

	void updateKidokuBySubscriptionId(Long subscriptionId);
}