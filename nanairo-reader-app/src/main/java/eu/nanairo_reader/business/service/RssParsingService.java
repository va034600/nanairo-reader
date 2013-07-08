package eu.nanairo_reader.business.service;

import eu.nanairo_reader.bean.FeedResult;

public interface RssParsingService {
	FeedResult getFeedResult(String rss);
}