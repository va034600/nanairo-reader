package eu.nanairo_reader.data.dao;

import java.io.Serializable;
import java.util.List;

public class BaseDaoMock<ENTITY extends Serializable, KEY extends Serializable> implements BaseDao<ENTITY, KEY> {
	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY parameter) {
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
