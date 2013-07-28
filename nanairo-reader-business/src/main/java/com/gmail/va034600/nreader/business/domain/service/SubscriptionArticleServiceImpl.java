package com.gmail.va034600.nreader.business.domain.service;

import java.util.List;

import javax.inject.Inject;

import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDao;
import com.gmail.va034600.nreader.data.entity.SubscriptionArticleEntity;


public class SubscriptionArticleServiceImpl implements SubscriptionArticleService{
	@Inject
	SubscriptionArticleDao subscriptionArticleDao;

	@Override
	public void deleteTheOld(long subscriptionId, int count) {
		this.subscriptionArticleDao.deleteTheOld(subscriptionId, count);
	}

	@Override
	public void addSubscriptionArticle(long subscriptionId, long articleId) {
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		subscriptionArticleEntity.setArticleId(articleId);
		this.subscriptionArticleDao.add(subscriptionArticleEntity);
	}

	@Override
	public void updateKidokuBySubscriptionId(long subscriptionId) {
		this.subscriptionArticleDao.updateKidokuBySubscriptionId(subscriptionId);
	}

	@Override
	public void deleteBySubscriptionId(long subscriptionId) {
		SubscriptionArticleEntity subscriptionArticleEntity = new SubscriptionArticleEntity();
		subscriptionArticleEntity.setSubscriptionId(subscriptionId);
		this.subscriptionArticleDao.delete(subscriptionArticleEntity);
	}

	@Override
	public long findSubscriptionIdByArticleId(long articleId) {
		SubscriptionArticleEntity parameter = new SubscriptionArticleEntity();
		parameter.setArticleId(articleId);
		List<SubscriptionArticleEntity> subscriptionArticleEntitieList = this.subscriptionArticleDao.findList(parameter);
		long subscriptionId = subscriptionArticleEntitieList.get(0).getSubscriptionId();
		return subscriptionId;
	}

}
