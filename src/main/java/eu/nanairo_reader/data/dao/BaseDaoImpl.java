package eu.nanairo_reader.data.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import eu.nanairo_reader.NanairoApplication;

public abstract class BaseDaoImpl<ENTITY, KEY> implements BaseDao<ENTITY, KEY> {
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

	private String getTableName() {
		Class<?> clazz = createEntity().getClass();
		String tableName = clazz.getSimpleName();
		tableName = tableName.substring(0, tableName.length() - 6);
		return tableName;
	}

	private String[] getColumnNames() {
		Class<?> clazz = createEntity().getClass();
		Field[] fields = clazz.getDeclaredFields();
		String[] columns = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			// TODO キャメルケースからスネークケースへ変更
			columns[i] = fields[i].getName();
		}
		return columns;
	}

	abstract protected ENTITY createEntity();

	public BaseDaoImpl() {
		// TODO
		this.db = NanairoApplication.db;
	}

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY param) {
		try {
			List<ENTITY> list = new ArrayList<ENTITY>();
			Class<?> clazz = createEntity().getClass();
			Field[] fields = clazz.getDeclaredFields();

			String tableName = getTableName();
			String[] columns = getColumnNames();
			Cursor cursor = db.query(tableName, columns, null, null, null, null, null);

			while (cursor.moveToNext()) {
				ENTITY entity = createEntity();
				for (Field field : fields) {
					field.setAccessible(true);
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
