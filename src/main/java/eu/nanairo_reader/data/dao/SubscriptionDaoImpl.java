package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoImpl extends BaseDaoImpl<SubscriptionEntity, Integer> implements SubscriptionDao {
	@Override
	protected SubscriptionEntity getEntity() {
		return new SubscriptionEntity();
	}

	@Override
	public List<SubscriptionEntity> getList() {
		return super.findList(null);
	}

}
