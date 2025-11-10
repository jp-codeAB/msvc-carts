package com.springcloud.msvc_carts.infrastructure.persistence.repository;

import com.springcloud.msvc_carts.infrastructure.persistence.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByCustomerId(Long customerId);
    List<CartEntity> findByAbandonedAtIsNullAndUpdatedAtBefore(LocalDateTime timestamp);
}
