package eu.nanairo_reader.data.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoMock extends BaseDaoMock<SubscriptionEntity> implements SubscriptionDao {
	@Override
	public List<SubscriptionEntity> getList() {
		List<SubscriptionEntity> list = new ArrayList<SubscriptionEntity>();

		SubscriptionEntity subscription1 = new SubscriptionEntity();
		subscription1.setId(1);
		subscription1.setTitle("注目まとめ（総合） - NAVER まとめ");
		subscription1.setUrl("http://matome.naver.jp/feed/hot");
		list.add(subscription1);

		SubscriptionEntity subscription2 = new SubscriptionEntity();
		subscription2.setId(2);
		subscription2.setTitle("TBN　-Today's Best News-");
		subscription2.setUrl("http://tbn17.com/index.rdf");
		list.add(subscription2);

		return list;
	}
}
