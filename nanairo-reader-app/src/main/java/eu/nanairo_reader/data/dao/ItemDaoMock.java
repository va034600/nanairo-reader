package eu.nanairo_reader.data.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;

public class ItemDaoMock extends BaseDaoMock<ItemEntity, Long> implements ItemDao {
	@Override
	public List<ItemEntity> getList(long id) {
		List<ItemEntity> list = new ArrayList<ItemEntity>();

		ItemEntity item1 = new ItemEntity();
		item1.setId(1L);
		item1.setTitle("6月16日(日)");
		item1.setLink("http://tbn17.com/archives/1764915.html");
		item1.setContent("bbb<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aaaa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa");
		item1.setMidoku(1);
		list.add(item1);

		ItemEntity item2 = new ItemEntity();
		item2.setId(2L);
		item2.setTitle("Google");
		item2.setLink("http://tbn17.com/archives/1764915.html");
		item2.setContent("bbb<br>aa");
		item2.setMidoku(0);
		list.add(item2);

		if (1 == id) {
			ItemEntity item3 = new ItemEntity();
			item3.setId(3L);
			item3.setTitle("y");
			item3.setMidoku(0);
			list.add(item3);
		} else {
			ItemEntity item3 = new ItemEntity();
			item3.setId(3L);
			item3.setTitle("g");
			item3.setMidoku(0);
			list.add(item3);
		}

		return list;
	}

	@Override
	public int getMidokuCount(long id) {
		return 3;
	}
}
