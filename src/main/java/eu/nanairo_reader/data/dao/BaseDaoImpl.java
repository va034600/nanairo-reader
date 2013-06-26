package eu.nanairo_reader.data.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDaoImpl<ENTITY, KEY> implements BaseDao<ENTITY, KEY> {
	/***/
	private SQLiteDatabase db;

	/***/
	private Field[] fields;

	/***/
	private String tableName;

	/***/
	private String[] columns;

	public BaseDaoImpl() {
		Class<?> clazz = getEntityClass();

		// table name
		this.tableName = clazz.getSimpleName();
		this.tableName = this.tableName.substring(0, tableName.length() - 6);

		// columns
		this.fields = clazz.getDeclaredFields();
		this.columns = new String[this.fields.length];
		for (int i = 0; i < this.fields.length; i++) {
			Field field = this.fields[i];
			field.setAccessible(true);
			// TODO キャメルケースからスネークケースへ変更
			this.columns[i] = this.fields[i].getName();
		}
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	abstract protected Class<ENTITY> getEntityClass();

	private static Object getValue(Cursor cursor, Field field) {
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

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY param) {
		try {
			String where = "";
			List<String> whereArgList = new ArrayList<String>();
			if (param != null) {
				for (int i = 0; i < this.fields.length; i++) {
					Field field = this.fields[i];
					Object object = field.get(param);
					if (object == null) {
						continue;
					}

					//
					String column = this.columns[i];
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
			return queryForList(this.tableName, this.columns, where, whereArgs);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("error", e);
		}
	}

	private static <RESULT> List<RESULT> cursorToList(Cursor cursor, Class<RESULT> resultClass) {
		try {
			List<RESULT> list = new ArrayList<RESULT>();
			while (cursor.moveToNext()) {
				RESULT entity = resultClass.newInstance();
				Field[] fields = resultClass.getDeclaredFields();
				for (Field field : fields) {
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

	protected List<ENTITY> queryForList(String table, String[] cs, String where, String[] whereArgs) {
		Cursor cursor = db.query(table, cs, where, whereArgs, null, null, null);
		List<ENTITY> list = cursorToList(cursor, getEntityClass());
		cursor.close();
		return list;
	}

	protected List<ENTITY> queryForList(String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		List<ENTITY> list = cursorToList(cursor, getEntityClass());
		cursor.close();
		return list;
	}

	protected int queryForInt(String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		if(!cursor.moveToNext()){
			//TODO なかったときの扱いはruntime exception?
			cursor.close();
			return 0;
		}
		int result = cursor.getInt(0);
		cursor.close();
		return result;
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
