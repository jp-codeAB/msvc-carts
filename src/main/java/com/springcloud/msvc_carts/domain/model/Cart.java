package com.springcloud.msvc_carts.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    private Long id;
    private Long customerId;
    @Setter
    private List<CartItem> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime abandonedAt;

    public Cart(Long customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void addItem(Long productId, int quantity) {
        Optional<CartItem> existingItem = this.items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            this.items.add(new CartItem(null, productId, quantity));
        }
        this.updatedAt = LocalDateTime.now();
        this.abandonedAt = null;
    }

    public void removeItem(Long productId) {
        this.items.removeIf(item -> item.getProductId().equals(productId));
        this.updatedAt = LocalDateTime.now();
    }

    public void updateItemQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }
        this.items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    this.updatedAt = LocalDateTime.now();
                });
    }

    public void markAsAbandoned() {
        if (this.abandonedAt == null) {
            this.abandonedAt = LocalDateTime.now();
        }
    }
}