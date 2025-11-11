package com.springcloud.msvc_carts.infrastructure.web.controller;

import com.springcloud.msvc_carts.domain.model.Cart;
import com.springcloud.msvc_carts.domain.port.in.IManageCartUseCase;
import com.springcloud.msvc_carts.domain.port.in.IAbandonedCartUseCase;
import com.springcloud.msvc_carts.infrastructure.security.AuthUser;
import com.springcloud.msvc_carts.infrastructure.mapper.ICartMapper;
import com.springcloud.msvc_carts.infrastructure.web.dto.request.CartItemRequest;
import com.springcloud.msvc_carts.infrastructure.web.dto.request.CartItemUpdateRequest;
import com.springcloud.msvc_carts.infrastructure.web.dto.response.CartResponse;
import com.springcloud.msvc_carts.infrastructure.web.dto.response.CheckoutConfirmationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final IManageCartUseCase manageCartUseCase;
    private final ICartMapper mapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> getMyCart(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Fetching cart for authenticated user ID: {}", authUser.getId());
        Cart cart = manageCartUseCase.getCartByCustomerId(authUser.getId());
        return ResponseEntity.ok(mapper.toResponse(cart));
    }

    @PostMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> addItemToCart(
            @Valid @RequestBody CartItemRequest request,
            @AuthenticationPrincipal AuthUser authUser) {

        Long authenticatedCustomerId = authUser.getId();
        Cart updatedCart = manageCartUseCase.addProductToCart(
                authenticatedCustomerId,
                request.getProductId(),
                request.getQuantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(updatedCart));
    }

    @PutMapping("/items/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @PathVariable Long productId,
            @Valid @RequestBody CartItemUpdateRequest request,
            @AuthenticationPrincipal AuthUser authUser) {

        Cart updatedCart = manageCartUseCase.updateProductQuantity(
                authUser.getId(),
                productId,
                request.getQuantity()
        );
        return ResponseEntity.ok(mapper.toResponse(updatedCart));
    }

    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void removeItemFromCart(
            @PathVariable Long productId,
            @AuthenticationPrincipal AuthUser authUser) {

        manageCartUseCase.removeProductFromCart(authUser.getId(), productId);
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CheckoutConfirmationResponse> checkout(@AuthenticationPrincipal AuthUser authUser) {
        CheckoutConfirmationResponse response = manageCartUseCase.checkout(authUser.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void clearMyCart(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Clearing cart for authenticated user ID: {}", authUser.getId());
        manageCartUseCase.clearCart(authUser.getId());
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartResponse> getCartByAdmin(@PathVariable Long customerId) {
        log.warn("ADMIN access to cart for customer ID: {}", customerId);
        Cart cart = manageCartUseCase.getCartByCustomerId(customerId);
        return ResponseEntity.ok(mapper.toResponse(cart));
    }

    @GetMapping("/abandoned")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CartResponse>> getAbandonedCarts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime threshold) {
        List<Cart> abandonedCarts = ((IAbandonedCartUseCase) manageCartUseCase).findAbandonedCarts(threshold);
        List<CartResponse> response = abandonedCarts.stream().map(mapper::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}