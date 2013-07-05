package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

public class SubscriptionArticleDaoImpl extends BaseDaoImpl<SubscriptionArticleEntity, SubscriptionArticleEntity> implements SubscriptionArticleDao {
	@Override
	protected Class<SubscriptionArticleEntity> getEntityClass() {
		return SubscriptionArticleEntity.class;
	}
}
