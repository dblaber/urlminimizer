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
package org.da4.urlminimizer.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class URLVO {
	private String alias;
	private String destination;
	private String creatorClass;
	private String ip;
	private String creatorApiKey;
	private String userAgent;
	private Date timeCreated;
	private String referrer;
	boolean isSpam = false;
	
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	private Map<String, String> metaData = new HashMap<String, String>();

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map<String, String> getMetaData() {
		return metaData;
	}

	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}

	public String getCreatorClass() {
		return creatorClass;
	}

	public void setCreatorClass(String creatorClass) {
		this.creatorClass = creatorClass;
	}

	public boolean isSpam() {
		return isSpam;
	}

	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}

	public String getCreatorApiKey() {
		return creatorApiKey;
	}

	public void setCreatorApiKey(String creatorApiKey) {
		this.creatorApiKey = creatorApiKey;
	}

}
