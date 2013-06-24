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

	/***/
	protected Field[] fields;

	/***/
	protected String tableName;

	/***/
	protected String[] columns;

	public BaseDaoImpl() {
		// TODO
		this.db = NanairoApplication.db;

		Class<?> clazz = createEntity().getClass();

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

	abstract protected ENTITY createEntity();

	protected Object getValue(Cursor cursor, Field field) {
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
			List<ENTITY> list = new ArrayList<ENTITY>();

			String where = "";
			List<String> whereArgList = new ArrayList<String>();
			if(param != null){
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

			if(where.length() != 0){
				where = where.substring(0, where.length() - 4);
			}

			String[] whereArgs = whereArgList.toArray(new String[0]);

			Cursor cursor = db.query(this.tableName, this.columns, where, whereArgs, null, null, null);
			while (cursor.moveToNext()) {
				ENTITY entity = createEntity();
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
