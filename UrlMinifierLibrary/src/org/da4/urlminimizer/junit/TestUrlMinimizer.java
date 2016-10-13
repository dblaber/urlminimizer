package org.da4.urlminimizer.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.vo.ConfigVO;
import org.junit.Before;
import org.junit.Test;

public class TestUrlMinimizer {

	static UrlMinimizer mini = null;
	ConfigVO config = null;
	@Before
	public void setUp() throws Exception {
		if(mini == null)
			mini = new UrlMinimizer("/home/dmb/urlmini.xml");
		config = mini.getConfig();
	}

//	@Test
//	public void testUrlMinimizer() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testMinimize() {
		String small = mini.minimize("http://google.com");
		System.out.println("small url: " + small);
		assertEquals(config.getRootUrl() + "xyz", small);
		for(int i = 0; i < 100; i++)
		{
		small = mini.minimize("http://google2.com" + i);
		System.out.println("small url" + i + ": " + small);
		assertNotNull(small);
		}
	}

	@Test
	public void testMaximize() {
		String small = mini.maximize("xyz");
		System.out.println("big url: " + small);
		assertEquals("http://google.com", small);
	}

}
