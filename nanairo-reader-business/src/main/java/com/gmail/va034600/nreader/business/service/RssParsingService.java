package com.gmail.va034600.nreader.business.service;

import com.gmail.va034600.nreader.business.exception.RssParsingException;
import com.gmail.va034600.nreader.business.vo.FeedResult;

public interface RssParsingService {
	FeedResult getFeedResult(String rss) throws RssParsingException;
}