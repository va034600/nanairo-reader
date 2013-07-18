package eu.nanairo_reader.business.service;

import static eu.nanairo_reader.business.constant.NanairoBusinessConstant.MIDOKU_OFF;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.bean.Article;

public class ArticleListManager {
	private List<Article> articleList = new ArrayList<Article>();

	public List<Article> getArticleList() {
		return articleList;
	}

	public void add(Article article) {
		this.articleList.add(article);
	}

	public void kidoku(long articleId) {
		for (Article article : this.articleList) {
			if (article.getId() == articleId) {
				article.setMidoku(MIDOKU_OFF);
				return;
			}
		}
	}

	public void kidokuAll(long subscriptionId) {
		for (Article article : this.articleList) {
			article.setMidoku(MIDOKU_OFF);
		}
	}
}
