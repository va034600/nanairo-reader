package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.bean.FeedItem;

public class RssParsingServiceMock implements RssParsingService {
	@Override
	public List<FeedItem> getItemList(String rss) {
		List<FeedItem> result = new ArrayList<FeedItem>();

		FeedItem feedItem = new FeedItem();
		feedItem.setTitle("title1");
		feedItem.setContent("a<br>b");
		feedItem.setUri("http://tbn17.com/archives/1764915.html");
		result.add(feedItem);

		FeedItem feedItem2 = new FeedItem();
		feedItem2.setTitle("title2");
		feedItem2.setContent("aa<br>bb");
		feedItem2.setUri("http://tbn17.com/archives/1764915.html");
		result.add(feedItem2);

		return result;
	}
}
