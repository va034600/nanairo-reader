package eu.nanairo.orm;

import java.util.List;

public interface BaseDao<ENTITY, KEY> {
	ENTITY findByPrimaryKey(KEY key);

	List<ENTITY> findList(ENTITY parameter);

	int add(ENTITY entity);

	int update(ENTITY entity);

	int delete(ENTITY entity);
}