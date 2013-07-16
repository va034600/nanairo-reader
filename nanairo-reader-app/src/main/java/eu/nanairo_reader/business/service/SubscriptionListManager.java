package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.bean.Subscription;

public class SubscriptionListManager {
	private List<Subscription> subscriptionList = new ArrayList<Subscription>();

	private List<Article> articleList = new ArrayList<Article>();

	public List<Subscription> getSubscriptionList() {
		return subscriptionList;
	}

	public void setSubscriptionList(List<Subscription> subscriptionList) {
		this.subscriptionList = subscriptionList;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

}
