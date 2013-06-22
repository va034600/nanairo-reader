package eu.nanairo_reader.data.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<ENTITY extends Serializable, KEY extends Serializable> {
	ENTITY findByPrimaryKey(KEY key);

	List<ENTITY> findList(ENTITY entity);

	int add(ENTITY entity);

	int update(ENTITY entity);

	int delete(ENTITY entity);
}