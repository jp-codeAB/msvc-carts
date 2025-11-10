package com.springcloud.msvc_carts.domain.port.out;

import com.springcloud.msvc_carts.domain.model.Cart;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICartRepositoryPort {
    Cart save(Cart cart);
    Optional<Cart> findByCustomerId(Long customerId);
    List<Cart> findAbandonedBefore(LocalDateTime timestamp);
    Optional<Cart> findById(Long id);
    void delete(Cart cart);
}
