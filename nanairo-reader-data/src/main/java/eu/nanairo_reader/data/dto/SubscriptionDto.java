package eu.nanairo_reader.data.dto;

public class SubscriptionDto {
	/***/
	private Long id;

	/***/
	private String title;

	/***/
	private String url;

	/***/
	private Integer midokuCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getMidokuCount() {
		return midokuCount;
	}

	public void setMidokuCount(Integer midokuCount) {
		this.midokuCount = midokuCount;
	}
}
