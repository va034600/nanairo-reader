package eu.nanairo.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class NanairoDaoSupport {
	/***/
	private SQLiteDatabase db;

	public NanairoDaoSupport() {
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * クラス名からテーブル名を取得
	 * 
	 * @return テーブル名
	 */
	protected static String getTableName(Class<?> entityClass) {
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

	protected <RESULT> List<RESULT> findList(Class<RESULT> resultClass, RESULT param) {
		try {
			String[] columns = convertColumns(resultClass);
			String where = "";
			List<String> whereArgList = new ArrayList<String>();
			if (param != null) {
				Field[] fields = resultClass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					field.setAccessible(true);
					Object object = field.get(param);
					if (object == null) {
						continue;
					}

					//
					String column = columns[i];
					where += column + " = ? AND";

					//
					String value = object.toString();
					whereArgList.add(value);
				}
			}

			if (where.length() != 0) {
				where = where.substring(0, where.length() - 4);
			}

			String[] whereArgs = whereArgList.toArray(new String[0]);
			String tableName = getTableName(resultClass);

			String sql = "SELECT * FROM " + tableName;
			if (where.length() != 0) {
				sql += " WHERE " + where;
			}

			return queryForList(resultClass, sql, whereArgs);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("error", e);
		}
	}

	protected static <RESULT> List<RESULT> cursorToList(Class<RESULT> resultClass, Cursor cursor) {
		try {
			List<RESULT> list = new ArrayList<RESULT>();
			while (cursor.moveToNext()) {
				RESULT entity = resultClass.newInstance();

				for (int i = 0; i < cursor.getColumnCount(); i++) {
					String columnName = cursor.getColumnName(i);
					// TODO キャメルケースからスネークケースへ変更
					columnName = columnName.toLowerCase(Locale.ENGLISH);
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

	protected <RESULT> List<RESULT> queryForList(Class<RESULT> resultClass, String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		List<RESULT> list = cursorToList(resultClass, cursor);
		cursor.close();
		return list;
	}

	protected int queryForInt(String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		if (!cursor.moveToNext()) {
			// TODO なかったときの扱いはruntime exception?
			cursor.close();
			return 0;
		}
		int result = cursor.getInt(0);
		cursor.close();
		return result;
	}

}
