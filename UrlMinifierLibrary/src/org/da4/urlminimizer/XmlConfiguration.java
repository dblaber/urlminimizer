package org.da4.urlminimizer;

import java.io.File;
import java.io.IOException;

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.vo.ConfigVO;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlConfiguration {
	public ConfigVO getConfig(String filename) throws ConfigException
	{
		 SAXBuilder builder = new SAXBuilder();
		 try {
			builder.build(new File(filename));
		} catch (JDOMException e) {
			throw new ConfigException("Error Parsing Config File",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConfigException("Error Reading Config File",e);
		}
		 
	}
}
