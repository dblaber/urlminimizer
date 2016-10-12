package org.da4.urlminimizer;

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.vo.ConfigVO;

public interface IConfig {

	ConfigVO getConfig(String filename) throws ConfigException;

}