package com.gmail.va034600.nreader.business.service;

import java.util.List;

import javax.inject.Inject;

import com.gmail.va034600.nreader.business.bean.Subscription;
import com.gmail.va034600.nreader.business.util.NanairoDateUtils;
import com.gmail.va034600.nreader.business.vo.FeedResult;
import com.gmail.va034600.nreader.data.dao.SubscriptionDao;
import com.gmail.va034600.nreader.data.dto.SubscriptionDto;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;


public class SubscriptionServiceImpl implements SubscriptionService {
	@Inject
	SubscriptionDao subscriptionDao;

	@Inject
	SubscriptionListManager subscriptionListManager;

	@Override
	public void loadSubscriptionList() {
		List<SubscriptionDto> dtoList = this.subscriptionDao.findListAndMidokuCount();
		this.subscriptionListManager.clear();

		for (SubscriptionDto dto : dtoList) {
			Subscription subscription = convertDto(dto);
			this.subscriptionListManager.add(subscription);
		}
	}

	private Subscription convertDto(SubscriptionDto dto) {
		Subscription subscription = new Subscription();

		subscription.setId(dto.getId());
		subscription.setTitle(dto.getTitle());
		subscription.setPublishedDate(dto.getPublishedDate());
		subscription.setUrl(dto.getUrl());
		subscription.setMidokuCount(dto.getMidokuCount());

		return subscription;
	}

	@Override
	public void kidoku(long subscriptionId) {
		this.subscriptionListManager.kidoku(subscriptionId);
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.subscriptionListManager.kidokuAll(subscriptionId);
	}

	@Override
	public void deleteSubscription(Subscription subscription) {
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setId(subscription.getId());
		this.subscriptionDao.delete(subscriptionEntity);

		this.subscriptionListManager.remove(subscription);
	}

	@Override
	public Subscription addSubscription(String url, FeedResult feedResult) {
		// 購読追加
		SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
		subscriptionEntity.setUrl(url);
		subscriptionEntity.setTitle(feedResult.getTitle());
		subscriptionEntity.setPublishedDate(NanairoDateUtils.convertDateToString(feedResult.getPublishedDate()));
		long subscriptionId = this.subscriptionDao.add(subscriptionEntity);
		subscriptionEntity.setId(subscriptionId);

		// list 追加
		Subscription subscription = convertEntity(subscriptionEntity);
		this.subscriptionListManager.add(subscription);

		return subscription;
	}

	private Subscription convertEntity(SubscriptionEntity entity) {
		Subscription subscription = new Subscription();

		subscription.setId(entity.getId());
		subscription.setTitle(entity.getTitle());
		subscription.setUrl(entity.getUrl());
		subscription.setPublishedDate(entity.getPublishedDate());

		return subscription;
	}

	@Override
	public List<SubscriptionEntity> findList() {
		return this.subscriptionDao.findList(null);
	}

	@Override
	public SubscriptionEntity findByPrimaryKey(long subscriptionId) {
		return this.subscriptionDao.findByPrimaryKey(subscriptionId);
	}

	@Override
	public Long getNextSubscriptionId(long subscriptionId) {
		List<Subscription> list = this.subscriptionListManager.getSubscriptionList();
		for (int i = 0; i < list.size(); i++) {
			Subscription subscription = list.get(i);
			if (subscriptionId == subscription.getId()) {
				if (list.size() - 1 > i) {
					Subscription nextSubscription = this.subscriptionListManager.getSubscriptionList().get(i + 1);
					if (nextSubscription.getMidokuCount() > 0) {
						return nextSubscription.getId();
					}
				}
			}
		}
		return null;
	}
}
