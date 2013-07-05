package eu.nanairo_reader.data.entity;

/**
 * 
 * @author eisakuu
 * 
 */
public class ArticleEntity {
	/***/
	private Long id;

	/***/
	private String title;

	/***/
	private String content;

	/***/
	private String link;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMidoku() {
		return midoku;
	}

	public void setMidoku(Integer midoku) {
		this.midoku = midoku;
	}
}
