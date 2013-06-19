package eu.nanairo_reader.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.Subscription;

public class SubscriptionDaoMock implements SubscriptionDao {
	@Override
	public List<Subscription> getList() {
		List<Subscription> list = new ArrayList<Subscription>();

		Subscription subscription1 = new Subscription();
		subscription1.setId(1);
		subscription1.setTitle("注目まとめ（総合） - NAVER まとめ");
		subscription1.setUrl("http://matome.naver.jp/feed/hot");
		list.add(subscription1);

		Subscription subscription2 = new Subscription();
		subscription2.setId(2);
		subscription2.setTitle("TBN　-Today's Best News-");
		subscription2.setUrl("http://tbn17.com/index.rdf");
		list.add(subscription2);

		return list;
	}
}
