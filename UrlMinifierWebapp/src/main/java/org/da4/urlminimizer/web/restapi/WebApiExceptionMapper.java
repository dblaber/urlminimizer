package org.da4.urlminimizer.web.restapi;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.web.vo.jaxb.MinimizeResponse;

@Provider
public class WebApiExceptionMapper implements ExceptionMapper<Exception>  {
	private static final Logger logger = LogManager.getLogger(WebApiExceptionMapper.class);
	// enhance error handling in future, for now constants
	static final int ERROR_NOAPIKEY = 800;
	@Produces(MediaType.APPLICATION_XML)
	@Override
	public Response toResponse(Exception e) {
		logger.error("API Exception!!", e);
		MinimizeResponse response = new MinimizeResponse();
		if(e instanceof APIKeyNotFound)
		{
			response.setErrorCode(ERROR_NOAPIKEY);
			response.setError("API Key not found or inactive!");
		}else{
			response.setErrorCode(500);
			response.setError(e.getMessage());
		}
		return Response.ok(response).build();
	}

}
