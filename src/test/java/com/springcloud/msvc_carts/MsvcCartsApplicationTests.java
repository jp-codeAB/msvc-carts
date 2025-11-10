package com.springcloud.msvc_carts;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.springcloud.msvc_carts.infrastructure.integration.client.OrderFeignClient;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = MsvcCartsApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class MsvcCartsApplicationTests {

	@MockitoBean
	private OrderFeignClient orderFeignClient;


	@Test
	void contextLoads() {
	}
}