package eu.nanairo.orm.dao;

/**
 * 
 * @author eisakuu
 * 
 */
public class SampleEntity {
	/***/
	private Integer id;

	/***/
	private String title;

	/***/
	private String newTitle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}
}
