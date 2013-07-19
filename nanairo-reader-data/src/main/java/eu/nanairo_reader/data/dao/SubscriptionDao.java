package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.dto.SubscriptionDto;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public interface SubscriptionDao extends BaseDao<SubscriptionEntity, Long> {
	List<SubscriptionDto> findListAndMidokuCount();
}