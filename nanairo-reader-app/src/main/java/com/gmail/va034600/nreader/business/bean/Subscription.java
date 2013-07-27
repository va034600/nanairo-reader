package com.gmail.va034600.nreader.business.bean;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
@SuppressWarnings("serial")
public class Subscription implements Serializable {
	/***/
	private long id;

	/***/
	private String title;

	/***/
	private String publishedDate;

	/***/
	private String url;

	/***/
	private int midokuCount;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMidokuCount() {
		return midokuCount;
	}

	public void setMidokuCount(int midokuCount) {
		this.midokuCount = midokuCount;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
}
