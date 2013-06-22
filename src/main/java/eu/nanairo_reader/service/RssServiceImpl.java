package eu.nanairo_reader.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.entity.ItemEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class RssServiceImpl implements RssService {
	@Inject
	private SubscriptionDao subscriptionDao;

	@Inject
	private ItemDao itemDao;

	public List<Subscription> getSubscriptionList() {
		List<Subscription> result = new ArrayList<Subscription>();
		for (SubscriptionEntity entity : this.subscriptionDao.getList()) {
			Subscription subscription = new Subscription();

			subscription.setId(entity.getId());
			subscription.setTitle(entity.getTitle());
			subscription.setUrl(entity.getUrl());

			result.add(subscription);
		}
		return result;
	}

	@Override
	public List<Item> getItemList(int id) {
		List<Item> result = new ArrayList<Item>();
		for (ItemEntity entity : this.itemDao.getList(id)) {
			Item item = new Item();

			item.setTitle(entity.getTitle());
			item.setContent(entity.getContent());
			item.setLink(entity.getLink());

			result.add(item);
		}
		return result;
	}
}
