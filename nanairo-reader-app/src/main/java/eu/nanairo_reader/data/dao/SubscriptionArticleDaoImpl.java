package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

public class SubscriptionArticleDaoImpl extends BaseDaoImpl<SubscriptionArticleEntity, SubscriptionArticleEntity> implements SubscriptionArticleDao {
	@Override
	protected Class<SubscriptionArticleEntity> getEntityClass() {
		return SubscriptionArticleEntity.class;
	}

	@Override
	public void deleteTheOld(Long id, int count) {
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
		Object[] bindArgs = new Object[] { id, count };
		getNanairoTemplate().execSQL(sql, bindArgs);
	}
}
