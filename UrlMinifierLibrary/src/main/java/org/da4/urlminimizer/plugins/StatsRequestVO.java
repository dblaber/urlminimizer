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
package org.da4.urlminimizer.plugins;
/**
 * Request object that will be fed to stats engine
 * @author Darren Blaber
 *
 */
public class StatsRequestVO {	
	private String alias;
	private String userAgent;
	private String referer;
	private String ip;
	private boolean newUrl = false;

	
	public StatsRequestVO(String alias, String userAgent, String referer, String ip, boolean newUrl) {
		super();
		this.alias = alias;
		this.userAgent = userAgent;
		this.referer = referer;
		this.ip = ip;
		this.newUrl = newUrl;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isNewUrl() {
		return newUrl;
	}

	public void setNewUrl(boolean newUrl) {
		this.newUrl = newUrl;
	}
	
	

}
