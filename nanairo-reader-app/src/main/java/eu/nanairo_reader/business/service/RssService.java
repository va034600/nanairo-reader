package eu.nanairo_reader.business.service;

import java.util.List;

import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.bean.Subscription;

public interface RssService {
	void addSubscription(String url);

	List<Subscription> getSubscriptionList();

	List<Article> getArticleList(long id);

	void storeArticles();
}