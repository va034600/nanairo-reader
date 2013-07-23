package eu.nanairo_reader.business.service;

import eu.nanairo_reader.business.vo.FeedItem;

public interface ArticleService {
	public int getMidokuCount(long subscriptionId);

	public void loadArticleList(long subscriptionId);

	public boolean isDuplicated(String uri);

	public long addArticle(FeedItem feedItem);

	public void deleteBySucriptionId(long subscriptionId);

	public void deleteTheOld(long subscriptionId, int count);

	public void kidokuAll(long subscriptionId);

	public boolean kidoku(long articleId);
}
