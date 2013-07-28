package com.gmail.va034600.nreader.business.rss.service;

import com.gmail.va034600.nreader.business.rss.exception.RssParsingException;
import com.gmail.va034600.nreader.business.rss.vo.FeedResult;

public interface RssParsingService {
	FeedResult getFeedResult(String rss) throws RssParsingException;
}