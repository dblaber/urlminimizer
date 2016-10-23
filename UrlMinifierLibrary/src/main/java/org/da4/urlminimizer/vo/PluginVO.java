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
package org.da4.urlminimizer.vo;

import java.util.List;
import java.util.Map;

import org.da4.urlminimizer.Hook;

public class PluginVO {
	String clazz;
	List<Hook> hooks;

	public PluginVO(String clazz, List<Hook> hooks) {
		super();
		this.clazz = clazz;
		this.hooks = hooks;
	}

	public List<Hook> getHooks() {
		return hooks;
	}

	public void setHooks(List<Hook> hooks) {
		this.hooks = hooks;
	}

	Map<String, String> attributes;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}


	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "PluginVO [clazz=" + clazz + ", hooks=" + hooks + ", attributes=" + attributes + "]";
	}

}
