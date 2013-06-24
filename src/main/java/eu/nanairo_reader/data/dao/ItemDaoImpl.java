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
		String sql = "";
		sql += "SELECT ITEM.* ";
		sql += "FROM ITEM INNER JOIN SUBSCRIPTION_ITEM ";
		sql += "ON ITEM.ID = SUBSCRIPTION_ITEM.ITEM_ID ";
		sql += "WHERE SUBSCRIPTION_ITEM.SUBSCRIPTION_ID = ?";
		String[] selectionArgs = { Integer.toString(id) };
		//TODO 自動でやりたい
		List<ItemEntity> list = queryForList(sql, selectionArgs);
		return list;
	}

	@Override
	public int getMidokuCount(Integer id) {
		// TODO 直接countできるメソッド用意
		return getList(id).size();
	}
}
