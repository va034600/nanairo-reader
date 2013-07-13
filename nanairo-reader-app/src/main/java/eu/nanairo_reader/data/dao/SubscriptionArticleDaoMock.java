package eu.nanairo_reader.data.dao;

import eu.nanairo_reader.data.entity.SubscriptionArticleEntity;

public class SubscriptionArticleDaoMock extends BaseDaoMock<SubscriptionArticleEntity, SubscriptionArticleEntity> implements SubscriptionArticleDao {
	@Override
	public void deleteTheOld(Long id, int count) {
	}
}
