package eu.nanairo_reader.business.service;

import java.util.List;

import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.bean.Subscription;

public interface RssService {
	List<Subscription> loadSubscriptionList();

	List<Article> loadArticleList(long subscriptionId);

	void storeArticles();

	void storeArticle(long subscriptionId);

	boolean addSubscription(String url);

	void kidoku(long articleId);

	void kidokuAll(long subscriptionId);

	void deleteSubscription(Subscription subscription);
}