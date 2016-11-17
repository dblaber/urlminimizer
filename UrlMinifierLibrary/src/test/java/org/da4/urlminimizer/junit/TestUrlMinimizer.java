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
package org.da4.urlminimizer.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.exception.APIKeyNotFound;
import org.da4.urlminimizer.exception.AliasDisabledException;
import org.da4.urlminimizer.exception.AliasNotFound;
import org.da4.urlminimizer.vo.ConfigVO;
import org.junit.Before;
import org.junit.Test;

public class TestUrlMinimizer {

	static UrlMinimizer mini = null;
	ConfigVO config = null;

	@Before
	public void setUp() throws Exception {
		if (mini == null)
			mini = new UrlMinimizer("/home/dmb/urlmini.xml");
		config = mini.getConfig();
	}

	// @Test
	// public void testUrlMinimizer() {
	// fail("Not yet implemented");
	// }

	@Test
	public void testMinimize() throws AliasDisabledException, APIKeyNotFound{
		Map<String,String> clientMetadata = new HashMap<String,String>();
		clientMetadata.put("CLIENT_KEY", "WEBGUI");
		String small = mini.minimize("http://google.com",clientMetadata);
		System.out.println("small url: " + small);
		assertEquals(config.getRootUrl() + "xyz", small);
		for (int i = 0; i < 100; i++) {
			small = mini.minimize("http://google2.com" + i,clientMetadata);
			System.out.println("small url" + i + ": " + small);
			assertNotNull(small);
		}
	}

	@Test
	public void testMaximize() throws AliasDisabledException,APIKeyNotFound, AliasNotFound {
		String small = mini.maximize("xyz", new HashMap<String,String>());
		System.out.println("big url: " + small);
		assertEquals("http://google.com", small);

		small = mini.maximize("68", new HashMap<String,String>());
		System.out.println("big url2: " + small);
		assertNotNull(small);
	}

}
