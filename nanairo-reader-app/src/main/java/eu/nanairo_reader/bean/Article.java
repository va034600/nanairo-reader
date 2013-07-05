package eu.nanairo_reader.bean;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
@SuppressWarnings("serial")
public class Article implements Serializable {
	/***/
	private String title;

	/***/
	private String content;

	/***/
	private String link;

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
}
