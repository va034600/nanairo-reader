package eu.nanairo_reader;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;

public class RssServiceImpl implements RssService {
	public List<Subscription> getSubscriptionList() {
		Subscription yahoo = new Subscription();
		yahoo.setTitle("TBN　-Today's Best News-");
		yahoo.setUrl("http://tbn17.com/index.rdf");

		Subscription google = new Subscription();
		google.setTitle("注目まとめ（総合） - NAVER まとめ");
		google.setUrl("http://matome.naver.jp/feed/hot");

		List<Subscription> list = new ArrayList<Subscription>();
		list.add(yahoo);
		list.add(google);

		return list;
	}

	@Override
	public List<Item> getItemList() {
		Item yahoo = new Item();
		yahoo.setTitle("Yahoo");

		Item google = new Item();
		google.setTitle("Google");

		List<Item> list = new ArrayList<Item>();
		list.add(yahoo);
		list.add(google);

		return list;
	}
}
