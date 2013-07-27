package com.gmail.va034600.nreader.business.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

import com.gmail.va034600.nreader.business.exception.RssParsingException;
import com.gmail.va034600.nreader.business.vo.FeedItem;
import com.gmail.va034600.nreader.business.vo.FeedResult;

import android.util.Log;

public class RssParsingService2Impl implements RssParsingService {
	@Override
	public FeedResult getFeedResult(String url) throws RssParsingException {
		Log.i("rss start", url);

		RssParser rss = new RssParser();

		try {
			FeedResult result = new FeedResult();
			List<FeedItem> feedItemList = new ArrayList<FeedItem>();
			RssFeed feed = rss.load(url);

			RssChannelBean channel = feed.getChannel();
			result.setTitle(channel.getTitle());
			result.setPublishedDate(channel.getPubDate());

			List<RssItemBean> items = feed.getItems();
			Collections.reverse(items);
			for (RssItemBean item : items) {
				FeedItem feedItem = new FeedItem();
				feedItem.setTitle(item.getTitle());
				feedItem.setLink(item.getLink());
				feedItem.setPublishedDate(item.getPubDate());
				feedItem.setContent(item.getDescription());
				feedItemList.add(feedItem);
			}
			result.setFeedItemList(feedItemList);

			Log.i("rss end", url);
			return result;

		} catch (Exception e) {
			throw new RssParsingException("rss", e);
		}
	}
}
