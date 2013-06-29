package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo.orm.BaseDao;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public interface SubscriptionDao extends BaseDao<SubscriptionEntity, Integer> {
	List<SubscriptionEntity> getList();
}