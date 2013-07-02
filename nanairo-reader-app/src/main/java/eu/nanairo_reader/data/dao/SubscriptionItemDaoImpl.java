package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionItemEntity;

public class SubscriptionItemDaoImpl extends BaseDaoImpl<SubscriptionItemEntity, SubscriptionItemEntity> implements SubscriptionItemDao {
	@Override
	protected Class<SubscriptionItemEntity> getEntityClass() {
		return SubscriptionItemEntity.class;
	}
}
