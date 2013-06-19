package eu.nanairo_reader.dao;

import java.util.List;

import eu.nanairo_reader.bean.Item;

public interface ItemDao {
	List<Item> getList(int id);
}