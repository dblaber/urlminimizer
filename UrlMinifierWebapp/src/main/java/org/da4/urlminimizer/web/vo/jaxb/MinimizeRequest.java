package org.da4.urlminimizer.web.vo.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MinimizeRequest {
	private String apiKey;
	private String url;
	private String[] metadata;
	private String client;
	private int apiVersion;
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMetadata(String[] metadata) {
		this.metadata = metadata;
	}
	@XmlElement(required=true)
	public String getApiKey() {
		return apiKey;
	}
	@XmlElement
	public String getUrl() {
		return url;
	}

	public String[] getMetadata() {
		return metadata;
	}

	public int getApiVersion() {
		return apiVersion;
	}
	@XmlElement(required=true)
	public void setApiVersion(int version) {
		this.apiVersion = version;
	}

	
	
}
