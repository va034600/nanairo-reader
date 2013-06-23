package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;

public class ItemDaoImpl extends BaseDaoImpl<ItemEntity, Integer> implements ItemDao {
	@Override
	protected ItemEntity createEntity() {
		return new ItemEntity();
	}

	@Override
	public List<ItemEntity> getList(int id) {
		return super.findList(null);
	}

	@Override
	public int getMidokuCount(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return 3;
	}
}
