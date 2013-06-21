package eu.nanairo_reader.data.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;

public class ItemDaoMock implements ItemDao {
	@Override
	public List<ItemEntity> getList(int id) {
		List<ItemEntity> list = new ArrayList<ItemEntity>();

		ItemEntity item1 = new ItemEntity();
		item1.setTitle("6月16日(日)");
		item1.setLink("http://tbn17.com/archives/1764915.html");
		item1.setContent("bbb<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aaaa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa");
		list.add(item1);

		ItemEntity item2 = new ItemEntity();
		item2.setTitle("Google");
		item2.setLink("http://tbn17.com/archives/1764915.html");
		item2.setContent("bbb<br>aa");
		list.add(item2);

		if(1 == id){
			ItemEntity item3 = new ItemEntity();
			item3.setTitle("y");
			list.add(item3);
		}else{
			ItemEntity item3 = new ItemEntity();
			item3.setTitle("g");
			list.add(item3);
		}

		return list;
	}
}