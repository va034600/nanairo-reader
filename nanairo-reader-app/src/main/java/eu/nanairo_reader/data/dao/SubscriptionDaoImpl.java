package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoImpl extends BaseDaoImpl<SubscriptionEntity, Long> implements SubscriptionDao {
	protected Class<SubscriptionEntity> getEntityClass() {
		return SubscriptionEntity.class;
	}
}
