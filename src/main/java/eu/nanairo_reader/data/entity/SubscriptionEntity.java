package eu.nanairo_reader.data.entity;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
public class SubscriptionEntity implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

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
