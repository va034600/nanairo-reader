package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedItem;
import eu.nanairo_reader.business.vo.FeedResult;

public class RssParsingServiceMock implements RssParsingService {
	@Override
	public FeedResult getFeedResult(String rss) throws RssParsingException {
		FeedResult result = new FeedResult();

		List<FeedItem> feedItemList = new ArrayList<FeedItem>();

		if (rss.startsWith("ha")) {
			result.setTitle("Test Title");

			FeedItem feedItem3 = new FeedItem();
			feedItem3.setTitle("titlee");
			feedItem3.setContent("aa<br>bb");
			feedItem3.setUri("http://aaaa");
			feedItemList.add(feedItem3);
		} else {
			result.setTitle("tbn");

			FeedItem feedItem = new FeedItem();
			feedItem.setTitle("title1");
			feedItem.setContent("a<br>b");
			feedItem.setUri("http://tbn17.com/archives/1764915.html");
			feedItemList.add(feedItem);

			FeedItem feedItem2 = new FeedItem();
			feedItem2.setTitle("title2");
			feedItem2.setContent("aa<br>bb");
			feedItem2.setUri("http://tbn17.com/archives/1764916.html");
			feedItemList.add(feedItem2);
		}

		result.setFeedItemList(feedItemList);

		return result;
	}
}
