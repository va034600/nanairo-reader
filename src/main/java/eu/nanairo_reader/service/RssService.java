package eu.nanairo_reader.service;

import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public interface RssService {
	List<SubscriptionEntity> getSubscriptionList();

	List<ItemEntity> getItemList(int id);
}