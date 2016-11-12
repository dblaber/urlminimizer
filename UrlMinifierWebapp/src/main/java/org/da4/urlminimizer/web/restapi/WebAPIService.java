package org.da4.urlminimizer.web.restapi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.exception.AliasDisabledException;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.web.vo.jaxb.MinimizeRequest;
import org.da4.urlminimizer.web.vo.jaxb.MinimizeResponse;

import com.google.common.net.HttpHeaders;

@Path("/minimizerapi")
public class WebAPIService {
	private static final Logger logger = LogManager.getLogger(WebAPIService.class);
	@Context
	ServletContext context;
	@Context
	HttpServletRequest request;

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
	public MinimizeResponse minimizeUrl(@Valid MinimizeRequest miniRequest)
			throws MalformedURLException, APIKeyNotFound, AliasDisabledException {

		MinimizeResponse response = new MinimizeResponse();
		if(miniRequest.getApiKey().equalsIgnoreCase("WEBGUI"))
			throw new APIKeyNotFound("APIKEY not allowed");
		UrlMinimizer minimizer = (UrlMinimizer) context.getAttribute("minimizer");
		String url = miniRequest.getUrl();
		if (url == null || url.trim().equals(""))
			throw new RuntimeUrlException("Empty Url!");
		logger.debug("Raw Url: " + url);
		if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://"))
			url = "http://" + url;
		logger.debug("Converted url: " + url);
		String protocol = new URL(url).getProtocol();
		logger.debug("Protocol: " + protocol);
		if (!(protocol.equals("http") || protocol.equals("https"))) {
			logger.debug("Invalid URL!");
			throw new RuntimeUrlException("Invalid URL");
		}
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		Map<String, String> clientMetadata = new HashMap<String, String>();
		clientMetadata.put("CLIENT_KEY", miniRequest.getApiKey());
		clientMetadata.put("REFERER", request.getHeader(HttpHeaders.REFERER));
		clientMetadata.put("IP", ipAddress);
		clientMetadata.put("USER_AGENT", request.getHeader("User-Agent"));
		String mini = minimizer.minimize(url, clientMetadata);
		logger.debug("Minified URL: " + mini);

		response.setMiniUrl(mini);
		response.setErrorCode(200);
		response.setApiKey(miniRequest.getApiKey());
		response.setOriginalUrl(miniRequest.getUrl());
		response.setCorrectedUrl(url);
		return response;
	}
}
