package eu.nanairo_reader.business.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import eu.nanairo_reader.bean.FeedItem;

public class RssParsingServiceImpl implements RssParsingService {
	@Override
	public List<FeedItem> getItemList(String rss) {
		Log.i("rss start", rss);

		List<FeedItem> result = new ArrayList<FeedItem>();
		try {
			URL feedUrl = new URL(rss);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			List<Object> entries = feed.getEntries();
			Iterator iterator = entries.listIterator();
			while (iterator.hasNext()) {
				FeedItem feedItem = new FeedItem();
				SyndEntry ent = (SyndEntry) iterator.next();

				feedItem.setTitle(ent.getTitle());
				feedItem.setAuthor(feed.getAuthor());

				if(ent.getContents().size() > 0){
					SyndContent contents = (SyndContent) ent.getContents().get(0);
					feedItem.setContent(contents.getValue());
				}

				feedItem.setCopyright(feed.getCopyright());
				feedItem.setFeedType(feed.getFeedType());
				feedItem.setUri(feed.getUri());
				feedItem.setLink(feed.getLink());

				result.add(feedItem);
			}

		} catch (MalformedURLException e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("rss", e);
		} catch (IllegalArgumentException e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("rss", e);
		} catch (FeedException e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("rss", e);
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
			throw new RuntimeException("rss", e);
		}

		Log.i("rss end", rss);
		return result;
	}
}
