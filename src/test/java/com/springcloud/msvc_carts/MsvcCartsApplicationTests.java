package com.springcloud.msvc_carts;

import com.springcloud.msvc_carts.infrastructure.integration.client.OrderFeignClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(classes = {MsvcCartsApplication.class, MsvcCartsApplicationTests.MockConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class MsvcCartsApplicationTests {

	@TestConfiguration
	static class MockConfig {
		@Mock
		private OrderFeignClient orderFeignClient;

		@Bean
		public OrderFeignClient orderFeignClient() {
			return orderFeignClient;
		}
	}

	@Test
	void contextLoads() {
	}
}