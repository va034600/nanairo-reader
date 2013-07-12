package eu.nanairo_reader.business.vo;

import java.util.List;

/**
 * 
 * @author eisakuu
 * 
 */
public class FeedResult {
	/***/
	private String title;

	/***/
	private List<FeedItem> feedItemList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FeedItem> getFeedItemList() {
		return feedItemList;
	}

	public void setFeedItemList(List<FeedItem> feedItemList) {
		this.feedItemList = feedItemList;
	}
}
