package eu.nanairo_reader.data.dao;

import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;
import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

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
		sql += "ORDER BY ARTICLE_ID ";
		sql += "LIMIT -1 OFFSET ?";
		sql += ")";
		Object[] bindArgs = new Object[] { subscriptionId, count };
		getNanairoTemplate().execSQL(sql, bindArgs);
	}

	@Override
	public void updateKidokuBySubscriptionId(Long subscriptionId) {
		String sql = "";
		sql += "UPDATE ARTICLE SET ";
		sql += "MIDOKU = ? ";
		sql += "WHERE ";
		sql += "ID IN (";
		sql += "SELECT ARTICLE_ID FROM SUBSCRIPTION_ARTICLE ";
		sql += "WHERE SUBSCRIPTION_ID = ? ";
		sql += ")";
		Object[] bindArgs = new Object[] { MIDOKU_OFF, subscriptionId };
		getNanairoTemplate().execSQL(sql, bindArgs);
	}
}
