package com.springcloud.msvc_carts.domain.port.out;

import com.springcloud.msvc_carts.domain.model.CartItem;

import java.util.List;

public interface IOrderServicePort {
    Long createOrderFromCart(Long customerId, List<CartItem> items);
}