package eu.nanairo_reader.business.service;

import java.util.List;

import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.vo.FeedResult;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

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
