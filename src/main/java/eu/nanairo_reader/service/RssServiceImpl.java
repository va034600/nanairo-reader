package eu.nanairo_reader.service;

import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.entity.ItemEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class RssServiceImpl implements RssService {
	@Inject
	private SubscriptionDao subscriptionDao;

	@Inject
	private ItemDao itemDao;

	public List<SubscriptionEntity> getSubscriptionList() {
		return this.subscriptionDao.getList();
	}

	@Override
	public List<ItemEntity> getItemList(int id) {
		return this.itemDao.getList(id);
	}
}
