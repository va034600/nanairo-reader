package eu.nanairo_reader.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.Item;

public class ItemDaoImpl implements ItemDao {
	@Override
	public List<Item> getList(String url) {
		List<Item> list = new ArrayList<Item>();

		Item item1 = new Item();
		item1.setTitle("6月16日(日)");
		item1.setLink("http://tbn17.com/archives/1764915.html");
		item1.setContent("bbb<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aaaa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa");
		list.add(item1);

		Item item2 = new Item();
		item2.setTitle("Google");
		item2.setLink("http://tbn17.com/archives/1764915.html");
		item2.setContent("bbb<br>aa");
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
