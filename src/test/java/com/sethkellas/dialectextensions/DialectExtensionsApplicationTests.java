package com.sethkellas.dialectextensions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DialectExtensionsApplicationTests {

	@Test
	void canOnlyLoadProperlyWhenUsingTestProfile() {
		
	}
	
}
