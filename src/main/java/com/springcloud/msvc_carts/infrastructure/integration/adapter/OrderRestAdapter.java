package com.springcloud.msvc_carts.infrastructure.integration.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.msvc_carts.domain.model.CartItem;
import com.springcloud.msvc_carts.domain.port.out.IOrderServicePort;
import com.springcloud.msvc_carts.infrastructure.integration.client.OrderFeignClient;
import com.springcloud.msvc_carts.infrastructure.web.dto.request.OrderCreationRequest;
import com.springcloud.msvc_carts.infrastructure.web.dto.request.OrderItemCreationRequest;
import com.springcloud.msvc_carts.shared.exception.BusinessException;
import com.springcloud.msvc_carts.shared.exception.ExternalServiceException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderRestAdapter implements IOrderServicePort {
    private final OrderFeignClient orderClient;

    @Override
    public Long createOrderFromCart(Long customerId, List<CartItem> items) {
        List<OrderItemCreationRequest> orderItems = items.stream()
                .map(item -> new OrderItemCreationRequest(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        OrderCreationRequest request = new OrderCreationRequest(customerId, orderItems);

        try {
            return orderClient.createOrder(request);
        } catch (FeignException e) {
            if (e.status() == 400 || e.status() == 409) {
                String detailMessage = extractErrorMessage(e);
                throw new BusinessException("Order creation failed: " + detailMessage, e);
            }
            throw new ExternalServiceException("Failed to communicate with Order Service.", e);
        }
    }

    private String extractErrorMessage(FeignException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> errorBody = mapper.readValue(
                    e.contentUTF8(),
                    new TypeReference<Map<String, String>>() {}
            );
            return errorBody.getOrDefault("message", "Unknown reason.");
        } catch (Exception ex) {
            return e.getMessage();
        }
    }
}
