package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.bean.Article;

public class ArticleListManager {
	private List<Article> articleList = new ArrayList<Article>();

	public List<Article> getArticleList() {
		return articleList;
	}
}
