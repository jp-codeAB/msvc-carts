package com.springcloud.msvc_carts.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {
    private Long customerId;
    private List<OrderItemCreationRequest> items;
}