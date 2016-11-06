/*******************************************************************************
 * Copyright 2016 Darren Blaber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.da4.urlminimizer.web.vo.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@SuppressWarnings("serial")

@XmlRootElement
public class MinimizeResponse implements Serializable {
	private String miniUrl;
	private String originalUrl;
	private String correctedUrl;
	private String error;
	private int errorCode;
	private String apiKey;
	public MinimizeResponse() {
		
	}
	@XmlElement
	public String getCorrectedUrl() {
		return correctedUrl;
	}

	public void setCorrectedUrl(String correctedUrl) {
		this.correctedUrl = correctedUrl;
	}

	@XmlElement
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@XmlElement
	public String getMiniUrl() {
		return miniUrl;
	}
	public void setMiniUrl(String miniUrl) {
		this.miniUrl = miniUrl;
	}
	@XmlElement
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	@XmlElement
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@XmlElement
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
