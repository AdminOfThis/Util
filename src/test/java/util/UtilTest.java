package util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import test.SuperTest;

class UtilTest extends SuperTest {

	@Test
	void test() {
		System.out.println("This is a test");
		assertNotNull(System.out);
	}

}
