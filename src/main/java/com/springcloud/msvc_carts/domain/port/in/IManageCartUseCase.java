package com.springcloud.msvc_carts.domain.port.in;

import com.springcloud.msvc_carts.domain.model.Cart;
import com.springcloud.msvc_carts.infrastructure.web.dto.response.CheckoutConfirmationResponse;

public interface IManageCartUseCase {
    Cart getMyCart(Long customerId);
    Cart getCartByCustomerId(Long customerId);
    Cart addProductToCart(Long customerId, Long productId, int quantity);
    Cart updateProductQuantity(Long customerId, Long productId, int quantity);
    void removeProductFromCart(Long customerId, Long productId);
    void clearCart(Long customerId);
    CheckoutConfirmationResponse checkout(Long customerId);
}