package org.da4.urlminimizer.junit;

import static org.junit.Assert.*;

import org.da4.urlminimizer.UrlMinimizer;
import org.junit.Before;
import org.junit.Test;

public class TestUrlMinimizer {

	@Before
	public void setUp() throws Exception {
		UrlMinimizer mini = new UrlMinimizer("/home/dmb/urlmini.xml");
	}

	@Test
	public void testUrlMinimizer() {
		fail("Not yet implemented");
	}

	@Test
	public void testMinimize() {
		fail("Not yet implemented");
	}

	@Test
	public void testMaximize() {
		fail("Not yet implemented");
	}

}
