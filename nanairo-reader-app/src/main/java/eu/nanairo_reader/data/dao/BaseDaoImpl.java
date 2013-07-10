package eu.nanairo_reader.data.dao;

import java.lang.reflect.Field;
import java.util.List;

import eu.nanairo.orm.NanairoDaoSupport;

public abstract class BaseDaoImpl<ENTITY, KEY> extends NanairoDaoSupport implements BaseDao<ENTITY, KEY> {
	public BaseDaoImpl() {
	}

	abstract protected Class<ENTITY> getEntityClass();

	@Override
	public ENTITY findByPrimaryKey(KEY key) {
		// TODO パラメータどうする？難しい。
		ENTITY param;
		try {
			param = getEntityClass().newInstance();
			Field field = getEntityClass().getDeclaredField("id");
			field.setAccessible(true);
			field.set(param, key);
			
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("e", e);
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("e", e);
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("e", e);
		} catch (NoSuchFieldException e) {
			// TODO 自動生成された catch ブロック
			throw new RuntimeException("e", e);
		}
		
		List<ENTITY> entityList = findList(param);

		if (entityList.size() == 0) {
			return null;
		}

		return entityList.get(0);
	}

	@Override
	public List<ENTITY> findList(ENTITY param) {
		return super.getNanairoTemplate().findList(getEntityClass(), param);
	}

	protected List<ENTITY> queryForList(String sql, String[] selectionArgs) {
		return super.getNanairoTemplate().queryForList(getEntityClass(), sql, selectionArgs);
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
