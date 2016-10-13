package org.da4.urlminimizer.plugins.sql;

import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.vo.URLVO;

public interface IJDBCDAO {

	URLVO getDestinationUrlFromAlias(String alias);

	long getNextId();

	void persistUrl(URLVO dataObj);

	URLVO getAliasFromDestination(String destination) throws AliasNotFound;

}