package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;

public class ItemDaoImpl extends BaseDaoImpl<ItemEntity, Integer> implements ItemDao {
	@Override
	protected Class<ItemEntity> getEntityClass() {
		return ItemEntity.class;
	}

	@Override
	public List<ItemEntity> getList(int id) {
		String sql = "";
		sql += "SELECT ITEM.* ";
		sql += "FROM ITEM INNER JOIN SUBSCRIPTION_ITEM ";
		sql += "ON ITEM.ID = SUBSCRIPTION_ITEM.ITEM_ID ";
		sql += "WHERE SUBSCRIPTION_ITEM.SUBSCRIPTION_ID = ?";
		String[] selectionArgs = { Integer.toString(id) };
		// TODO 自動でやりたい
		return queryForList(sql, selectionArgs);
	}

	@Override
	public int getMidokuCount(Integer id) {
		String sql = "";
		sql += "SELECT COUNT(*) ";
		sql += "FROM ITEM INNER JOIN SUBSCRIPTION_ITEM ";
		sql += "ON ITEM.ID = SUBSCRIPTION_ITEM.ITEM_ID ";
		sql += "WHERE SUBSCRIPTION_ITEM.SUBSCRIPTION_ID = ? AND ITEM.MIDOKU = 1";
		String[] selectionArgs = { Integer.toString(id) };
		// TODO 自動でやりたい
		return queryForInt(sql, selectionArgs);
	}
}
