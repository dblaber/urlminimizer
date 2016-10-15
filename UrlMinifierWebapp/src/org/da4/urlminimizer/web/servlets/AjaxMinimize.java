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
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.exception.RuntimeUrlException;
import org.da4.urlminimizer.web.exception.RuntimeUrlWebException;
import org.da4.urlminimizer.web.vo.Response;

import com.google.gson.Gson;

/**
 * Servlet implementation class DoMinimize
 */
@WebServlet("/AjaxMinimize")
public class AjaxMinimize extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AjaxMinimize.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxMinimize() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		try{
		response.setContentType("application/json");
		UrlMinimizer minimizer =  (UrlMinimizer) request.getServletContext().getAttribute("minimizer");
		String url = request.getParameter("url");
		if(url == null || url.trim().equals(""))
			throw new RuntimeUrlException("Empty Url!");
		logger.debug("Raw Url: " + url);
		if(!url.toLowerCase().startsWith("http://"))
			url = "http://" + url;
		logger.debug("Converted url: " + url);
		String protocol = new URL(url).getProtocol();
		logger.debug("Protocol: " + protocol);
		if(!(protocol.equals("http") && !protocol.equals("https")))
		{
			logger.debug("Invalid URL!");
			throw new RuntimeUrlException("Invalid URL");
		}
		String mini = minimizer.minimize(url);
		logger.debug("Minified URL: " + mini);
		Response resp = new Response(url, mini);
		logger.trace("JSON Response: " + gson.toJson(resp));
		response.getWriter().append(gson.toJson(resp));
		} catch (RuntimeUrlWebException | org.da4.urlminimizer.exception.RuntimeUrlException e)
		{
			Response resp = new Response(null, null);
			resp.setError(e.getMessage());
			response.getWriter().append(gson.toJson(resp));
			return;
		} catch (Exception e)
		{

			Response resp = new Response(null, null);
			resp.setError("Unknown Error has occured");
			response.getWriter().append(gson.toJson(resp));
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
