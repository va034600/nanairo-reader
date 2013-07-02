package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.SubscriptionEntity;

public interface SubscriptionDao extends BaseDao<SubscriptionEntity, Long> {
	List<SubscriptionEntity> getList();
}