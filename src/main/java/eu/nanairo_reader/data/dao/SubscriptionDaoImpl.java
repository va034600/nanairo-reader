package eu.nanairo_reader.data.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoImpl extends BaseDaoImpl<SubscriptionEntity, Integer> implements SubscriptionDao {
	@Override
	public List<SubscriptionEntity> getList() {
		List<SubscriptionEntity> list = new ArrayList<SubscriptionEntity>();

		try {

			Class<SubscriptionEntity> clazz = SubscriptionEntity.class;
			Field[] fields = clazz.getDeclaredFields();
			String[] columns = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				columns[i] = fields[i].getName();
			}

			String tableName = clazz.getSimpleName();
			tableName = tableName.substring(0, tableName.length() - 6);
			Cursor cursor = super.db.query(tableName, columns, null, null, null, null, null);

			while (cursor.moveToNext()) {
				SubscriptionEntity subscription = new SubscriptionEntity();

				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					field.setAccessible(true);
					String propertyName = field.getName().toUpperCase(Locale.ENGLISH);
					Object value;
					Class<?> type = field.getType();
					System.out.println(type);
					int columnIndex = cursor.getColumnIndex(propertyName);
					if (type.equals(Integer.class)) {
						value = cursor.getInt(columnIndex);
					} else if (type.equals(Long.class)) {
						value = cursor.getLong(columnIndex);
					} else if (type.equals(Short.class)) {
						value = cursor.getShort(columnIndex);
					} else if (type.equals(Float.class)) {
						value = cursor.getFloat(columnIndex);
					} else if (type.equals(Double.class)) {
						value = cursor.getDouble(columnIndex);
					} else if (type.equals(String.class)) {
						value = cursor.getString(columnIndex);
					} else {
						value = cursor.getBlob(columnIndex);
					}

					field.set(subscription, value);
				}

				list.add(subscription);
			}

			cursor.close();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return list;
	}
}
