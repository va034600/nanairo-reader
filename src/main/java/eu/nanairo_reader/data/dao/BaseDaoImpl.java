package eu.nanairo_reader.data.dao;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import eu.nanairo_reader.NanairoApplication;

public class BaseDaoImpl<ENTITY extends java.io.Serializable> implements BaseDao<ENTITY> {
	/***/
	protected SQLiteDatabase db;

	public BaseDaoImpl() {
		// TODO
		this.db = NanairoApplication.db;
	}

	@Override
	public ENTITY findByPrimaryKey(ENTITY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
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
