package eu.nanairo_reader.service;

import java.util.List;

import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;

public interface RssService {
	List<Subscription> getSubscriptionList();

	List<Item> getItemList(int id);
}