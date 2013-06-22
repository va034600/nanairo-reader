package eu.nanairo_reader.data.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<ENTITY extends Serializable> {
	ENTITY findByPrimaryKey(ENTITY key);

	List<ENTITY> findList(ENTITY key);
	
	int add(ENTITY entity);
	
	int update(ENTITY entity);
	
	int delete(ENTITY entity);
}