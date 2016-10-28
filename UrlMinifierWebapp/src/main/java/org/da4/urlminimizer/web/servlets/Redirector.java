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
package org.da4.urlminimizer.web.servlets;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.UrlMinimizer;

import com.google.common.net.HttpHeaders;

/**
 * Servlet implementation class Redirector
 * This servlet handles all urls that hit root url that don't match a servlet or file
 */
@WebServlet("/Redirector.do")
public class Redirector extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Redirector.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Redirector() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get request url that was placed from the filter
		String reqUrl = (String) request.getAttribute("URL_ALIAS");
		if(reqUrl == null || reqUrl.trim().isEmpty())
		{
			response.sendRedirect("/");
			return;
		}
		try{
		UrlMinimizer minimizer =  (UrlMinimizer) request.getServletContext().getAttribute("minimizer");
		logger.debug("Alias Recieved: " + reqUrl);
		
	   String ipAddress = request.getHeader("X-FORWARDED-FOR");  
	   if (ipAddress == null) {  
	       ipAddress = request.getRemoteAddr();  
	   }
		Map<String,String> clientMetadata = new HashMap<String,String>();
		clientMetadata.put("REFFERER", request.getHeader(HttpHeaders.REFERER));
		clientMetadata.put("IP", ipAddress);
		clientMetadata.put("USER_AGENT", request.getHeader("User-Agent"));
		
		
		// remove / in servlet path
		String url = minimizer.maximize(reqUrl,clientMetadata);
		if(url == null || url.trim().isEmpty())
		{
			logger.debug("Unknown alias:" +reqUrl);
			response.sendRedirect("/");
			return;
		}
		logger.debug("Maximimized URL: " + url);
		response.sendRedirect(url);		
		}
		catch (Exception e){
			logger.error("Exception",e);
			response.sendRedirect("/");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}

}
