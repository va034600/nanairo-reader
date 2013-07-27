package com.gmail.va034600.nreader.business.service;

import com.gmail.va034600.nreader.business.vo.FeedResult;

public interface ArticleService {
	void loadArticleList(long subscriptionId);

	void deleteBySucriptionId(long subscriptionId);

	void kidokuAll(long subscriptionId);

	Long kidoku(long articleId);

	int addArticleListByFeed(long subscriptionId, FeedResult feedResult);
}
