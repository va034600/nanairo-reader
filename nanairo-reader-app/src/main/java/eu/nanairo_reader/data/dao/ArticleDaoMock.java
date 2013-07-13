package eu.nanairo_reader.data.dao;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.data.entity.ArticleEntity;

public class ArticleDaoMock extends BaseDaoMock<ArticleEntity, Long> implements ArticleDao {
	@Override
	public List<ArticleEntity> getList(long id) {
		List<ArticleEntity> list = new ArrayList<ArticleEntity>();

		ArticleEntity article1 = new ArticleEntity();
		article1.setId(1L);
		article1.setTitle("6月16日(日)");
		article1.setLink("http://tbn17.com/archives/1764915.html");
		article1.setContent("bbb<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aaaa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa");
		article1.setMidoku(1);
		list.add(article1);

		ArticleEntity article2 = new ArticleEntity();
		article2.setId(2L);
		article2.setTitle("Google");
		article2.setLink("http://tbn17.com/archives/1764915.html");
		article2.setContent("bbb<br>aa");
		article2.setMidoku(0);
		list.add(article2);

		if (1 == id) {
			ArticleEntity article3 = new ArticleEntity();
			article3.setId(3L);
			article3.setTitle("y");
			article3.setMidoku(0);
			list.add(article3);
		} else {
			ArticleEntity article3 = new ArticleEntity();
			article3.setId(3L);
			article3.setTitle("g");
			article3.setMidoku(0);
			list.add(article3);
		}

		return list;
	}

	@Override
	public int getMidokuCount(long id) {
		return 3;
	}

	@Override
	public void deleteTheOld(Long id, int count) {
	}
}
