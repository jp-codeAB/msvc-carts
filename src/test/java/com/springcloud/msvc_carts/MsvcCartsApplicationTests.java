package com.springcloud.msvc_carts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.springcloud.msvc_carts.infrastructure.integration.client.OrderFeignClient;

@SpringBootTest
class MsvcCartsApplicationTests {

	@MockBean
	private OrderFeignClient orderFeignClient;

	@Test
	void contextLoads() {
	}
}