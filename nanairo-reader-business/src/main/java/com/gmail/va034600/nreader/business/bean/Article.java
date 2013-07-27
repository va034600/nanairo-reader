package com.gmail.va034600.nreader.business.bean;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
@SuppressWarnings("serial")
public class Article implements Serializable {
	/***/
	private long id;

	/***/
	private String title;

	/***/
	private String content;

	/***/
	private String link;

	/***/
	private String publishedDate;

	/***/
	private Integer midoku;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Integer getMidoku() {
		return midoku;
	}

	public void setMidoku(Integer midoku) {
		this.midoku = midoku;
	}
}
