package eu.nanairo_reader.bean;

import java.io.Serializable;

/**
 * 
 * @author eisakuu
 * 
 */
public class Subscription implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

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
}
