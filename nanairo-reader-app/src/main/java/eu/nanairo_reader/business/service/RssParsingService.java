package eu.nanairo_reader.business.service;

import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedResult;

public interface RssParsingService {
	FeedResult getFeedResult(String rss) throws RssParsingException;
}