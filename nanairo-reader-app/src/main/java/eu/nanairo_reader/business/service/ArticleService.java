package eu.nanairo_reader.business.service;

import eu.nanairo_reader.business.vo.FeedResult;

public interface ArticleService {
	void loadArticleList(long subscriptionId);

	void deleteBySucriptionId(long subscriptionId);

	void kidokuAll(long subscriptionId);

	Long kidoku(long articleId);

	int addArticleListByFeed(long subscriptionId, FeedResult feedResult);
}
