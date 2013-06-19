package eu.nanairo_reader.service;

import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.dao.ItemDao;
import eu.nanairo_reader.dao.SubscriptionDao;

public class RssServiceImpl implements RssService {
	@Inject
	private SubscriptionDao subscriptionDao;

	@Inject
	private ItemDao itemDao;

	public List<Subscription> getSubscriptionList() {
		return this.subscriptionDao.getList();
	}

	@Override
	public List<Item> getItemList(String url) {
		return this.itemDao.getList(url);
	}
}
