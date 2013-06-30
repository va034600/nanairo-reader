package eu.nanairo.orm;

import java.util.List;

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
		return super.findList(getEntityClass(), param);
	}

	protected List<ENTITY> queryForList(String sql, String[] selectionArgs) {
		return super.queryForList(getEntityClass(), sql, selectionArgs);
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
