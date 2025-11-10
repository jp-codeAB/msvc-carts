package com.springcloud.msvc_carts.infrastructure.web.dto.response;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private Integer quantity;
}