package com.gmail.va034600.nreader.business.service;

import java.util.List;

import com.gmail.va034600.nreader.business.bean.Subscription;
import com.gmail.va034600.nreader.business.vo.FeedResult;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;


public interface SubscriptionService {
	void loadSubscriptionList();

	void kidoku(long subscriptionId);

	void kidokuAll(long subscriptionId);

	void deleteSubscription(Subscription subscription);

	Subscription addSubscription(String url, FeedResult feedResult);

	List<SubscriptionEntity> findList();

	SubscriptionEntity findByPrimaryKey(long subscriptionId);

	Long getNextSubscriptionId(long subscriptionId);
}
