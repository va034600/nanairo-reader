package eu.nanairo.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class NanairoDaoSupport {
	/***/
	protected SQLiteDatabase db;

	public NanairoDaoSupport() {
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * クラス名からテーブル名を取得
	 * @return テーブル名
	 */
	protected static String getTableName(Class<?> entityClass){
		// TODO スネークケースからキャメルケースへ変更
		String tableName = entityClass.getSimpleName();
		tableName = tableName.substring(0, tableName.length() - 6);
		return tableName;
	}

	protected static String[] convertColumns(Class<?> clazz) {
		// フィールドから列名を取得
		Field[] fields = clazz.getDeclaredFields();
		String[] columns = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// TODO スネークケースからキャメルケースへ変更
			columns[i] = field.getName();
		}
		return columns;
	}

	protected static Object getValue(Cursor cursor, Field field) {
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

	protected static <RESULT> List<RESULT> cursorToList(Cursor cursor, Class<RESULT> resultClass) {
		try {
			List<RESULT> list = new ArrayList<RESULT>();
			while (cursor.moveToNext()) {
				RESULT entity = resultClass.newInstance();

				for (int i = 0; i < cursor.getColumnCount(); i++) {
					String columnName = cursor.getColumnName(i);
					columnName = columnName.toLowerCase(Locale.ENGLISH);
					// TODO キャメルケースからスネークケースへ変更
					Field field = resultClass.getDeclaredField(columnName);
					field.setAccessible(true);
					Object value = getValue(cursor, field);
					field.set(entity, value);
				}
				list.add(entity);
			}
			return list;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("error", e);
		}
	}
}
