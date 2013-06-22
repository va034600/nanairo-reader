package eu.nanairo_reader.data.entity;

/**
 * 
 * @author eisakuu
 * 
 */
public class ItemEntity {
	/***/
	private Integer id;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
