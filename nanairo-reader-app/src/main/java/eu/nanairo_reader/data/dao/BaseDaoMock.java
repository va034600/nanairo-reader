package eu.nanairo_reader.data.dao;

import java.util.List;

public class BaseDaoMock<ENTITY, KEY> implements BaseDao<ENTITY, KEY> {
	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		return null;
	}

	@Override
	public List<ENTITY> findList(ENTITY parameter) {
		return null;
	}

	@Override
	public long add(ENTITY entity) {
		return 0;
	}

	@Override
	public int update(ENTITY entity) {
		return 0;
	}

	@Override
	public int delete(ENTITY entity) {
		return 0;
	}
}
