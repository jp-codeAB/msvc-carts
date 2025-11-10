package com.springcloud.msvc_carts.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class CartItem {

    private Long id;
    private Long productId;
    @Setter
    private int quantity;
}