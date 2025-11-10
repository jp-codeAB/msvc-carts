package com.springcloud.msvc_carts.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreationRequest {
    private Long productId;
    private Integer quantity;
}