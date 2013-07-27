package com.gmail.va034600.nreader.data.dao;

import static com.gmail.va034600.nreader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import static com.gmail.va034600.nreader.business.constant.NanairoBusinessConstant.MIDOKU_ON;

import com.gmail.va034600.nreader.data.entity.SubscriptionArticleEntity;

public class SubscriptionArticleDaoImpl extends BaseDaoImpl<SubscriptionArticleEntity, SubscriptionArticleEntity> implements SubscriptionArticleDao {
	@Override
	protected Class<SubscriptionArticleEntity> getEntityClass() {
		return SubscriptionArticleEntity.class;
	}

	@Override
	public void deleteTheOld(Long subscriptionId, int count) {
		// TODO INよりEXISTS使いたいけど、うまくいかない。 
		// TODO execSQLは実施件数がわからないので、rawQueryを使いたい。
		String sql = "";
		sql += "DELETE FROM SUBSCRIPTION_ARTICLE ";
		sql += "WHERE ";
		sql += "ARTICLE_ID IN (";
		sql += "SELECT ARTICLE_ID FROM SUBSCRIPTION_ARTICLE ";
		sql += "WHERE SUBSCRIPTION_ID = ? ";
		sql += "ORDER BY ARTICLE_ID DESC ";
		sql += "LIMIT -1 OFFSET ?";
		sql += ")";
		Object[] bindArgs = new Object[] { subscriptionId, count };
		getNanairoTemplate().execSQL(sql, bindArgs);
	}

	@Override
	public void updateKidokuBySubscriptionId(Long subscriptionId) {
		// TODO INよりEXISTS使いたいけど、うまくいかない。 
		String sql = "";
		sql += "UPDATE ARTICLE SET ";
		sql += "MIDOKU = ? ";
		sql += "WHERE ";
		sql += "MIDOKU = ? AND ";
		sql += "ID IN (";
		sql += "SELECT ARTICLE_ID FROM SUBSCRIPTION_ARTICLE ";
		sql += "WHERE SUBSCRIPTION_ID = ? AND ARTICLE.ID = SUBSCRIPTION_ARTICLE.ARTICLE_ID";
		sql += ")";
		Object[] bindArgs = new Object[] { MIDOKU_OFF, MIDOKU_ON, subscriptionId };
		getNanairoTemplate().execSQL(sql, bindArgs);
	}
}
