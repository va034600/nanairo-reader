package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.ArticleEntity;

public class ArticleDaoImpl extends BaseDaoImpl<ArticleEntity, Long> implements ArticleDao {
	@Override
	protected Class<ArticleEntity> getEntityClass() {
		return ArticleEntity.class;
	}

	@Override
	public List<ArticleEntity> getList(long subscriptionId) {
		String sql = "";
		sql += "SELECT ARTICLE.* ";
		sql += "FROM ARTICLE INNER JOIN SUBSCRIPTION_ARTICLE ";
		sql += "ON ARTICLE.ID = SUBSCRIPTION_ARTICLE.ARTICLE_ID ";
		sql += "WHERE SUBSCRIPTION_ARTICLE.SUBSCRIPTION_ID = ?";
		String[] selectionArgs = { Long.toString(subscriptionId) };
		// TODO 自動でやりたい
		return queryForList(sql, selectionArgs);
	}

	@Override
	public int getMidokuCount(long id) {
		String sql = "";
		sql += "SELECT COUNT(*) ";
		sql += "FROM ARTICLE INNER JOIN SUBSCRIPTION_ARTICLE ";
		sql += "ON ARTICLE.ID = SUBSCRIPTION_ARTICLE.ARTICLE_ID ";
		sql += "WHERE SUBSCRIPTION_ARTICLE.SUBSCRIPTION_ID = ? AND ARTICLE.MIDOKU = 1";
		String[] selectionArgs = { Long.toString(id) };
		// TODO 自動でやりたい
		return getNanairoTemplate().queryForInt(sql, selectionArgs);
	}
}
