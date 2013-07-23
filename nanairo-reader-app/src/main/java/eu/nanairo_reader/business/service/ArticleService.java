package eu.nanairo_reader.business.service;

import eu.nanairo_reader.business.vo.FeedResult;

public interface ArticleService {
	int getMidokuCount(long subscriptionId);

	void loadArticleList(long subscriptionId);

	void deleteBySucriptionId(long subscriptionId);

	void kidokuAll(long subscriptionId);

	Long kidoku(long articleId);

	void addArticleListByFeed(long subscriptionId, FeedResult feedResult);
}
