package com.springcloud.msvc_carts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MsvcCartsApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> MsvcCartsApplication.main(new String[] {}));
	}
}