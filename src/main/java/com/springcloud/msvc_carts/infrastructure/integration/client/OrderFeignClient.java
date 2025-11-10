package com.springcloud.msvc_carts.infrastructure.integration.client;

import com.springcloud.msvc_carts.infrastructure.web.dto.request.OrderCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-orders")
public interface OrderFeignClient {

    @PostMapping("/orders")
    Long createOrder(@RequestBody OrderCreationRequest request);
}