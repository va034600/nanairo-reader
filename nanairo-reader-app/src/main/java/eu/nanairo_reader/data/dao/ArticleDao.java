package eu.nanairo_reader.data.dao;

import java.util.List;

import eu.nanairo_reader.data.entity.ArticleEntity;

public interface ArticleDao extends BaseDao<ArticleEntity, Long> {
	List<ArticleEntity> getList(long id);

	int getMidokuCount(long id);

	void deleteTheOld(Long id, int count);
}