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
package org.da4.urlminimizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.vo.ConfigVO;
import org.da4.urlminimizer.vo.PluginVO;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlConfiguration implements IConfig {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.da4.urlminimizer.IConfig#getConfig(java.lang.String)
	 */
	@Override
	public ConfigVO getConfig(String filename) throws ConfigException {
		ConfigVO config = new ConfigVO();
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new File(filename));
		} catch (JDOMException e) {
			throw new ConfigException("Error Parsing Config File", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConfigException("Error Reading Config File", e);
		}

		Element rootElement = doc.getRootElement();
		Attribute attribProdName = rootElement.getAttribute("productName");
		if (attribProdName == null || attribProdName.getValue() == null || attribProdName.getValue().trim().isEmpty())
			throw new ConfigException("No Product name,or issue with product name");

		Attribute attribRootUrl = rootElement.getAttribute("rootUrl");
		if (attribRootUrl == null || attribRootUrl.getValue() == null || attribRootUrl.getValue().trim().isEmpty())
			throw new ConfigException("No Root Url,or issue with URL");

		config.setProductName(attribProdName.getValue());
		config.setRootUrl(attribRootUrl.getValue());

		List<PluginVO> pluginVos = new ArrayList<PluginVO>();
		List<Element> xmlPluginList = rootElement.getChildren("plugin");
		for (Element xmlPlugin : xmlPluginList) {
			PluginVO pluginVo = new PluginVO(xmlPlugin.getAttributeValue("class"),
					Hook.get(xmlPlugin.getAttributeValue("hook")));
			Map<String, String> attribMap = new LinkedHashMap<String, String>();
			List<Element> xmlPluginAttribs = xmlPlugin.getChildren("attribute");
			for (Element xmlAttribute : xmlPluginAttribs) {

				attribMap.put(xmlAttribute.getAttributeValue("name"), xmlAttribute.getAttributeValue("value"));
			}
			pluginVo.setAttributes(attribMap);
			pluginVos.add(pluginVo);

		}
		config.setPluginConfigs(pluginVos);
		return config;
	}
}
