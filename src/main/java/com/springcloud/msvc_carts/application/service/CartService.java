package com.springcloud.msvc_carts.application.service;

import com.springcloud.msvc_carts.domain.model.Cart;
import com.springcloud.msvc_carts.domain.port.in.IAbandonedCartUseCase;
import com.springcloud.msvc_carts.domain.port.in.IManageCartUseCase;
import com.springcloud.msvc_carts.domain.port.out.ICartRepositoryPort;
import com.springcloud.msvc_carts.domain.port.out.IOrderServicePort;
import com.springcloud.msvc_carts.infrastructure.web.dto.response.CheckoutConfirmationResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService implements IManageCartUseCase, IAbandonedCartUseCase {

    private final ICartRepositoryPort cartRepositoryPort;
    private final IOrderServicePort orderServicePort;

    @Override
    @Transactional(readOnly = true)
    public Cart getMyCart(Long customerId) {
        return cartRepositoryPort.findByCustomerId(customerId)
                .orElseGet(() -> new Cart(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepositoryPort.findByCustomerId(customerId)
                .orElseGet(() -> new Cart(customerId));
    }

    @Override
    public Cart addProductToCart(Long customerId, Long productId, int quantity) {
        Cart cart = getMyCart(customerId);
        cart.addItem(productId, quantity);
        return cartRepositoryPort.save(cart);
    }

    @Override
    public Cart updateProductQuantity(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Cart not found for customer: " + customerId));
        cart.updateItemQuantity(productId, quantity);
        return cartRepositoryPort.save(cart);
    }

    @Override
    public void removeProductFromCart(Long customerId, Long productId) {
        Cart cart = cartRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Cart not found for customer: " + customerId));
        cart.removeItem(productId);
        cartRepositoryPort.save(cart);
    }

    @Override
    public void clearCart(Long customerId) {
        Cart cart = cartRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Cart not found for customer: " + customerId));
        cartRepositoryPort.delete(cart);
    }

    @Override
    public CheckoutConfirmationResponse checkout(Long customerId) {

        Cart cart = cartRepositoryPort.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Cart not found for customer: " + customerId));
        LocalDateTime checkoutDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = checkoutDate.format(formatter);
        Long orderId = orderServicePort.createOrderFromCart(customerId, cart.getItems());
        cartRepositoryPort.delete(cart);
        String confirmationMessage = String.format(
                "¡Su orden ha sido creada con éxito! Número de la Orden: %d. Confirmada el %s.",
                orderId,
                formattedDate
        );
        return new CheckoutConfirmationResponse(orderId, confirmationMessage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> findAbandonedCarts(LocalDateTime threshold) {
        return cartRepositoryPort.findAbandonedBefore(threshold);
    }

}