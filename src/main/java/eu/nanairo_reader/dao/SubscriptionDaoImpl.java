package eu.nanairo_reader.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.Subscription;

public class SubscriptionDaoImpl implements SubscriptionDao {
	public List<Subscription> getList() {
		List<Subscription> list = new ArrayList<Subscription>();

		Subscription google = new Subscription();
		google.setTitle("注目まとめ（総合） - NAVER まとめ");
		google.setUrl("http://matome.naver.jp/feed/hot");
		list.add(google);

		Subscription yahoo = new Subscription();
		yahoo.setTitle("TBN　-Today's Best News-");
		yahoo.setUrl("http://tbn17.com/index.rdf");
		list.add(yahoo);

		return list;
	}
}
