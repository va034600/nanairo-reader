package eu.nanairo.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

public abstract class BaseDaoImpl<ENTITY, KEY> extends NanairoDaoSupport implements BaseDao<ENTITY, KEY> {
	public BaseDaoImpl() {
	}

	abstract protected Class<ENTITY> getEntityClass();

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY param) {
		try {
			String[] columns = convertColumns(getEntityClass());
			String where = "";
			List<String> whereArgList = new ArrayList<String>();
			if (param != null) {
				Field[] fields = getEntityClass().getDeclaredFields();
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
			String tableName = getTableName(getEntityClass());

			String sql = "SELECT * FROM " + tableName;
			if (where.length() != 0) {
				sql += " WHERE " + where;
			}

			return queryForList(sql, whereArgs);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("error", e);
		}
	}

	protected List<ENTITY> queryForList(String sql, String[] selectionArgs) {
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		List<ENTITY> list = cursorToList(cursor, getEntityClass());
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
