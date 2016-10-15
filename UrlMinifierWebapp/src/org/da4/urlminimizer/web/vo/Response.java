package org.da4.urlminimizer.web.vo;

public class Response {
	private String url;
	private String minifiedUrl;
	public Response(String url, String minifiedUrl) {
		super();
		this.url = url;
		this.minifiedUrl = minifiedUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMinifiedUrl() {
		return minifiedUrl;
	}
	public void setMinifiedUrl(String minifiedUrl) {
		this.minifiedUrl = minifiedUrl;
	}
	
	
}
