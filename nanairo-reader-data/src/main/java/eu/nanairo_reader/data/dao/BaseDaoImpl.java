package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo.orm.NanairoDaoSupport;

public abstract class BaseDaoImpl<ENTITY, KEY> extends NanairoDaoSupport implements BaseDao<ENTITY, KEY> {
	public BaseDaoImpl() {
	}

	abstract protected Class<ENTITY> getEntityClass();

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		return super.getNanairoTemplate().findByPrimaryKey(getEntityClass(), key);
	}

	@Override
	public List<ENTITY> findList(ENTITY param) {
		return super.getNanairoTemplate().findList(getEntityClass(), param);
	}

	protected List<ENTITY> queryForList(String sql, String[] selectionArgs) {
		return super.getNanairoTemplate().queryForList(getEntityClass(), sql, selectionArgs);
	}

	protected <RESULT> List<RESULT> queryForList(Class<RESULT> clazz, String sql, String[] selectionArgs) {
		return super.getNanairoTemplate().queryForList(clazz, sql, selectionArgs);
	}

	@Override
	public long add(ENTITY entity) {
		return super.getNanairoTemplate().add(getEntityClass(), entity);
	}

	@Override
	public int update(ENTITY entity) {
		return super.getNanairoTemplate().update(getEntityClass(), entity);
	}

	@Override
	public int delete(ENTITY entity) {
		return super.getNanairoTemplate().delete(getEntityClass(), entity);
	}
}
