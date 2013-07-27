package com.gmail.va034600.nreader.data.dao;

import java.util.List;

import com.gmail.va034600.nreader.data.dto.SubscriptionDto;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;


public interface SubscriptionDao extends BaseDao<SubscriptionEntity, Long> {
	List<SubscriptionDto> findListAndMidokuCount();
}