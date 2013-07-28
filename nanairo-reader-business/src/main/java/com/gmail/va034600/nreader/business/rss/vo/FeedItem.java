package com.gmail.va034600.nreader.business.rss.vo;

import java.util.Date;

/**
 * 
 * @author eisakuu
 * 
 */
public class FeedItem {
	/***/
	private String title;

	/***/
	private String content;

	/***/
	private String link;

	/***/
	private Date publishedDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
}
