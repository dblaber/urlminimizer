package org.da4.urlminimizer.plugins;

import java.util.Map;

import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;

public interface IPlugin {

	void init(Map<String, String> params);

	Object execute(Hook hook,Operation operation, Object input, Object output);

	void finished();

}