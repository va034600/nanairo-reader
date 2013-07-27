package com.gmail.va034600.nreader.data.dao;

import com.gmail.va034600.nreader.data.entity.SubscriptionArticleEntity;

public interface SubscriptionArticleDao extends BaseDao<SubscriptionArticleEntity, SubscriptionArticleEntity> {
	void deleteTheOld(Long subscriptionId, int count);

	void updateKidokuBySubscriptionId(Long subscriptionId);
}