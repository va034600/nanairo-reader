package eu.nanairo_reader.data.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import eu.nanairo_reader.NanairoApplication;

public abstract class BaseDaoImpl<ENTITY extends Serializable, KEY extends Serializable> implements BaseDao<ENTITY, KEY> {
	/***/
	protected SQLiteDatabase db;

	private Object getValue(Cursor cursor, Field field) {
		String propertyName = field.getName().toUpperCase(Locale.ENGLISH);
		int columnIndex = cursor.getColumnIndex(propertyName);
		Class<?> type = field.getType();
		if (type.equals(Integer.class)) {
			return cursor.getInt(columnIndex);
		} else if (type.equals(Long.class)) {
			return cursor.getLong(columnIndex);
		} else if (type.equals(Short.class)) {
			return cursor.getShort(columnIndex);
		} else if (type.equals(Float.class)) {
			return cursor.getFloat(columnIndex);
		} else if (type.equals(Double.class)) {
			return cursor.getDouble(columnIndex);
		} else if (type.equals(String.class)) {
			return cursor.getString(columnIndex);
		}
		return cursor.getBlob(columnIndex);
	}

	public BaseDaoImpl() {
		// TODO
		this.db = NanairoApplication.db;
	}

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	abstract protected ENTITY getEntity();

	@Override
	public List<ENTITY> findList(ENTITY param) {
		List<ENTITY> list = new ArrayList<ENTITY>();
		try {
			Class<?> clazz = getEntity().getClass();
			Field[] fields = clazz.getDeclaredFields();
			String[] columns = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				columns[i] = fields[i].getName();
			}

			String tableName = clazz.getSimpleName();
			tableName = tableName.substring(0, tableName.length() - 6);
			Cursor cursor = db.query(tableName, columns, null, null, null, null, null);

			while (cursor.moveToNext()) {
				ENTITY entity = getEntity();
				for (int i = 0; i < columns.length; i++) {
					Field field = fields[i];
					field.setAccessible(true);
					Object value = getValue(cursor, field);
					field.set(entity, value);
				}

				list.add(entity);
			}

			cursor.close();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int add(ENTITY entity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int update(ENTITY entity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int delete(ENTITY entity) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
}
