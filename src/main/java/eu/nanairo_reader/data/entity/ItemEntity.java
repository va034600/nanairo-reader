package eu.nanairo_reader.data.entity;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
public class ItemEntity implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

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
