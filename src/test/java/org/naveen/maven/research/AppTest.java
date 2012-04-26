package org.naveen.maven.research;

import org.junit.Test;

public class AppTest {
	
	@Test
	public void test() {
		System.out.println(new App().testing(32, null));
		System.out.println(new App().test("Test", null, 32));
	}
}
