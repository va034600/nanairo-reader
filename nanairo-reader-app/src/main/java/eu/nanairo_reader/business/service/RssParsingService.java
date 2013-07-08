package eu.nanairo_reader.business.service;

import eu.nanairo_reader.bean.FeedResult;
import eu.nanairo_reader.business.exception.RssParsingException;

public interface RssParsingService {
	FeedResult getFeedResult(String rss) throws RssParsingException;
}