package eu.nanairo_reader.business.service;

import java.util.List;

import eu.nanairo_reader.bean.FeedItem;

public interface RssParsingService {
	List<FeedItem> getArticleList(String rss);
}