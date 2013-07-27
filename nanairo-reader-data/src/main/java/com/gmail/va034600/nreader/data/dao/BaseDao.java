package com.gmail.va034600.nreader.data.dao;

import java.util.List;

public interface BaseDao<ENTITY, KEY> {
	ENTITY findByPrimaryKey(KEY key);

	List<ENTITY> findList(ENTITY parameter);

	long add(ENTITY entity);

	int update(ENTITY entity);

	int delete(ENTITY entity);
}