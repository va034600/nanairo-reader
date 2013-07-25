package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.business.vo.FeedResult;

public class RssParsingServiceMock implements RssParsingService {
	@Override
	public FeedResult getFeedResult(String rss) throws RssParsingException {
		if (!rss.startsWith("h")) {
			throw new RssParsingException(rss);
		}

		FeedResult result = new FeedResult();
		result.setPublishedDate(new Date());

		List<FeedItem> feedItemList = new ArrayList<FeedItem>();

		if (rss.startsWith("ha")) {
			result.setTitle("Test Title");

			final int FEED_COUNT = 30;
			for (int i = 0; i < FEED_COUNT; i++) {
				FeedItem feedItem = new FeedItem();
				feedItem.setTitle("title" + i);
				feedItem.setPublishedDate(new Date());
				feedItem.setContent("aa<br>bb" + i);
				feedItem.setLink("http://test?" + i);
				feedItemList.add(feedItem);
			}
		} else {
			result.setTitle("tbn");

			FeedItem feedItem = new FeedItem();
			feedItem.setTitle("title1");
			feedItem.setPublishedDate(new Date());
			feedItem.setContent("a<br>b");
			feedItem.setLink("http://tbn17.com/archives/1764915.html");
			feedItemList.add(feedItem);

			FeedItem feedItem2 = new FeedItem();
			feedItem2.setTitle("title2");
			feedItem2.setPublishedDate(new Date());
			feedItem2.setContent("aa<br>bb");
			feedItem2.setLink("http://tbn17.com/archives/1764916.html");
			feedItemList.add(feedItem2);
		}

		result.setFeedItemList(feedItemList);

		return result;
	}
}
