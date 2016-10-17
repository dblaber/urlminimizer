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

import org.da4.urlminimizer.exception.ConfigException;
import org.da4.urlminimizer.vo.ConfigVO;
/**
 * Interface that describes all configuration related methods
 * @author dmb
 *
 */
public interface IConfig {
/**
 * Method that must be implemented that will read, parse and populate ConfigVO object
 * @param filename Filname that will be read and parsed
 * @return ConfigVO object of parsed config data
 * @throws ConfigException Any error while reading or parsing config file
 */
	ConfigVO getConfig(String filename) throws ConfigException;

}