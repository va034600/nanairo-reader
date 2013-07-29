package com.gmail.va034600.nreader.data.dao;

import static com.gmail.va034600.nreader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import java.util.List;

import com.gmail.va034600.nreader.data.dto.SubscriptionDto;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;


public class SubscriptionDaoImpl extends BaseDaoImpl<SubscriptionEntity, Long> implements SubscriptionDao {
	protected Class<SubscriptionEntity> getEntityClass() {
		return SubscriptionEntity.class;
	}

	@Override
	public List<SubscriptionDto> findListAndMidokuCount() {
		String sql = "";
		sql += "SELECT SUBSCRIPTION.*, COUNT(ARTICLE.ID) AS MIDOKU_COUNT ";
		sql += "FROM SUBSCRIPTION ";
		sql += "LEFT JOIN SUBSCRIPTION_ARTICLE ";
		sql += "ON SUBSCRIPTION.ID = SUBSCRIPTION_ARTICLE.SUBSCRIPTION_ID ";
		sql += "LEFT JOIN ARTICLE ";
		sql += "ON SUBSCRIPTION_ARTICLE.ARTICLE_ID = ARTICLE.ID AND ARTICLE.MIDOKU = ? ";
		sql += "GROUP BY SUBSCRIPTION.ID ";
		sql += "ORDER BY SUBSCRIPTION.ID DESC";
		return queryForList(SubscriptionDto.class, sql, new String[]{Integer.toString(MIDOKU_ON)});
	}
}
