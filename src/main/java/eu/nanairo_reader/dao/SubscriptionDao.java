package eu.nanairo_reader.dao;

import java.util.List;

import eu.nanairo_reader.bean.Subscription;

public interface SubscriptionDao {
	List<Subscription> getList();
}