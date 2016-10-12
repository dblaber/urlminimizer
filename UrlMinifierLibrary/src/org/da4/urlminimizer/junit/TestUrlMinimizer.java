package org.da4.urlminimizer.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.da4.urlminimizer.UrlMinimizer;
import org.da4.urlminimizer.vo.ConfigVO;
import org.junit.Before;
import org.junit.Test;

public class TestUrlMinimizer {

	UrlMinimizer mini = null;
	ConfigVO config = null;
	@Before
	public void setUp() throws Exception {
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
	}

	@Test
	public void testMaximize() {
		String small = mini.maximize("xyz");
		System.out.println("big url: " + small);
		assertEquals("http://google.com", small);
	}

}
