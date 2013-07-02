package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssImageBean;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

import android.util.Log;
import eu.nanairo_reader.bean.FeedItem;

public class RssParsing2ServiceImpl implements RssParsingService {
	@Override
	public List<FeedItem> getItemList(String url) {
		Log.i("rss start", url);

		List<FeedItem> result = new ArrayList<FeedItem>();
		try {
			RssParser rss = new RssParser();
			RssFeed feed = rss.load(url);

			RssChannelBean channel = feed.getChannel();
			System.out.println("Feed Title: " + channel.getTitle());

			// Gets the image of the feed and display the image URL
			RssImageBean image = feed.getImage();
			System.out.println("Feed Image: " + image.getUrl());

			// Gets and iterate the items of the feed
			List<RssItemBean> items = feed.getItems();
			for (int i = 0; i < items.size(); i++) {
				RssItemBean item = items.get(i);
				FeedItem feedItem = new FeedItem();
				feedItem.setTitle(item.getTitle());
				result.add(feedItem);
			}

		} catch (Exception e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("rss", e);
		}

		Log.i("rss end", url);
		return result;
	}
}
