package org.da4.urlminimizer.web.vo.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MinimizeRequest {
	@XmlElement(required=true)
	private String apiKey;
	@XmlElement(required=true)
	private String url;
	@XmlElement
	private String[] metadata;
	public String getApiKey() {
		return apiKey;
	}

	public String getUrl() {
		return url;
	}

	public String[] getMetadata() {
		return metadata;
	}

	
	
}
