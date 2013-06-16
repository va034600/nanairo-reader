package eu.nanairo_reader.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;

public class RssServiceImpl implements RssService {
	public List<Subscription> getSubscriptionList() {
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

	@Override
	public List<Item> getItemList(String url) {
		List<Item> list = new ArrayList<Item>();

		Item item1 = new Item();
		item1.setTitle("6月16日(日)");
		item1.setContent("bbb<br>aa");
		item1.setLink("http://tbn17.com/archives/1764915.html");
		list.add(item1);

		Item item2 = new Item();
		item2.setTitle("Google");
		list.add(item2);

		if("http://matome.naver.jp/feed/hot".equals(url)){
			Item item3 = new Item();
			item3.setTitle("y");
			list.add(item3);
		}else{
			Item item3 = new Item();
			item3.setTitle("g");
			list.add(item3);
		}

		return list;
	}
}
