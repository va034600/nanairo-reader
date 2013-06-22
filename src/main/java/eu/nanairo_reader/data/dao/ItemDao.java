package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.ItemEntity;

public interface ItemDao extends BaseDao<ItemEntity> {
	List<ItemEntity> getList(int id);
}