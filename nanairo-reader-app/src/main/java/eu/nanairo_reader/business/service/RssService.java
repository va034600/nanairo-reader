package eu.nanairo_reader.business.service;

import java.util.List;

import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.bean.Subscription;

public interface RssService {
	List<Subscription> getSubscriptionList();

	List<Article> getArticleList(long id);

	void storeArticles();

	boolean addSubscription(String url);

	void kidoku(long id);
}