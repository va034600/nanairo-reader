package com.gmail.va034600.nreader.data.dao;

import java.util.List;

import com.gmail.va034600.nreader.data.entity.ArticleEntity;


public interface ArticleDao extends BaseDao<ArticleEntity, Long> {
	List<ArticleEntity> getListBySubscriptionId(long subscriptionId);

	int getMidokuCount(long subscriptionId);

	void deleteTheOld(Long subscriptionId, int count);

	void deleteBySucriptionId(Long subscriptionId);
}