package eu.nanairo_reader.data.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import eu.nanairo_reader.data.entity.ItemEntity;

public class ItemDaoImpl extends BaseDaoImpl<ItemEntity, Integer> implements ItemDao {
	@Override
	protected ItemEntity createEntity() {
		return new ItemEntity();
	}

	@Override
	public List<ItemEntity> getList(int id) {
		try {
			List<ItemEntity> list = new ArrayList<ItemEntity>();

			String sql = "";
			sql += "SELECT ITEM.* ";
			sql += "FROM ITEM INNER JOIN SUBSCRIPTION_ITEM ";
			sql += "ON ITEM.ID = SUBSCRIPTION_ITEM.ITEM_ID ";
			sql += "WHERE SUBSCRIPTION_ITEM.SUBSCRIPTION_ID = ?";
			String[] whereArgs = { Integer.toString(id) };

			Cursor cursor = db.rawQuery(sql, whereArgs);
			while (cursor.moveToNext()) {
				ItemEntity entity = createEntity();
				for (Field field : this.fields) {
					Object value = getValue(cursor, field);
					field.set(entity, value);
				}

				list.add(entity);
			}

			cursor.close();
			return list;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("error", e);
		}
	}

	@Override
	public int getMidokuCount(Integer id) {
		// TODO 直接countできるメソッド用意
		return getList(id).size();
	}
}
