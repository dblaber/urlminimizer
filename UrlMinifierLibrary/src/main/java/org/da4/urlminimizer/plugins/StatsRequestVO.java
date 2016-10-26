package org.da4.urlminimizer.plugins;
/**
 * Request object that will be fed to stats engine
 * @author Darren Blaber
 *
 */
public class StatsRequestVO {	
	private String alias;
	private String userAgent;
	private String referrer;
	private String ip;
	
	public StatsRequestVO(String alias, String userAgent, String referrer, String ip) {
		super();
		this.alias = alias;
		this.userAgent = userAgent;
		this.referrer = referrer;
		this.ip = ip;
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
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

}
