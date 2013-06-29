package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo.orm.dao.BaseDaoImpl;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoImpl extends BaseDaoImpl<SubscriptionEntity, Integer> implements SubscriptionDao {
	protected Class<SubscriptionEntity> getEntityClass() {
		return SubscriptionEntity.class;
	}

	@Override
	public List<SubscriptionEntity> getList() {
		return super.findList(null);
	}

}
