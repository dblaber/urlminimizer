package org.da4.urlminimizer.web.servlets;


import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.web.vo.Response;

import com.google.gson.Gson;

/**
 * Servlet implementation class DoMinimize
 */
@WebServlet("/AjaxMinimize")
public class AjaxMinimize extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
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
		response.setContentType("application/json");
		Gson gson = new Gson();
		UrlMinimizer minimizer =  (UrlMinimizer) request.getServletContext().getAttribute("minimizer");
		String url = request.getParameter("url");
		System.out.println("Raw Url: " + url);
		if(!url.toLowerCase().startsWith("http://"))
			url = "http://" + url;
		String protocol = new URL(url).getProtocol();
		if(!protocol.equals("http") || !protocol.equals("https"))
		{
			System.out.println("Invalid URL!");
		}
		System.out.println("URL: " + url);
		String mini = minimizer.minimize(url);
		Response resp = new Response(url, mini);
		response.getWriter().append(gson.toJson(resp));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
