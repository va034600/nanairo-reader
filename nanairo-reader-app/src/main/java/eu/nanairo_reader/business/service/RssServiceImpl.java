package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.nanairo_reader.bean.FeedItem;
import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionItemDao;
import eu.nanairo_reader.data.entity.ItemEntity;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.data.entity.SubscriptionItemEntity;

public class RssServiceImpl implements RssService {
	@Inject
	private SubscriptionDao subscriptionDao;

	@Inject
	private ItemDao itemDao;

	@Inject
	private SubscriptionItemDao subscriptionItemDao;

	@Inject
	private RssParsingService rssParsingService;

	public List<Subscription> getSubscriptionList() {
		List<Subscription> result = new ArrayList<Subscription>();
		for (SubscriptionEntity entity : this.subscriptionDao.getList()) {
			Subscription subscription = new Subscription();

			subscription.setId(entity.getId());
			subscription.setTitle(entity.getTitle());
			subscription.setUrl(entity.getUrl());
			int midokuCount = this.itemDao.getMidokuCount(entity.getId());
			subscription.setMidokuCount(midokuCount);

			result.add(subscription);
		}
		return result;
	}

	@Override
	public List<Item> getItemList(long id) {
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

	@Override
	public void storeItems() {
		for (SubscriptionEntity entity : this.subscriptionDao.getList()) {
			List<FeedItem> feedItemList = this.rssParsingService.getItemList(entity.getUrl());
			for (FeedItem feedItem : feedItemList) {
				ItemEntity itemEntity = new ItemEntity();

				itemEntity.setTitle(feedItem.getTitle());
				itemEntity.setContent(feedItem.getContent());
				itemEntity.setLink(feedItem.getUri());
				//TODO マジックNo
				itemEntity.setMidoku(1);
				// TODO

				long itemId = this.itemDao.add(itemEntity);

				// TODO
				SubscriptionItemEntity subscriptionItemEntity = new SubscriptionItemEntity();
				subscriptionItemEntity.setSubscriptionId(entity.getId());
				subscriptionItemEntity.setItemId(itemId);
				this.subscriptionItemDao.add(subscriptionItemEntity);
			}
		}
	}
}
