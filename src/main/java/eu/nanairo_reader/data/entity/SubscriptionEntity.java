package eu.nanairo_reader.data.entity;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
@SuppressWarnings("serial")
public class SubscriptionEntity implements Serializable {
	/***/
	private int id;

	/***/
	private String title;

	/***/
	private String url;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
