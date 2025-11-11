package com.springcloud.msvc_carts.infrastructure.persistence.adapter;

import com.springcloud.msvc_carts.domain.model.Cart;
import com.springcloud.msvc_carts.domain.port.out.ICartRepositoryPort;
import com.springcloud.msvc_carts.infrastructure.mapper.ICartMapper;
import com.springcloud.msvc_carts.infrastructure.persistence.entity.CartEntity;
import com.springcloud.msvc_carts.infrastructure.persistence.repository.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartAdapter implements ICartRepositoryPort {
    private final ICartRepository jpaRepository;
    private final ICartMapper mapper;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = mapper.toEntity(cart);
        if (cart.getId() != null) {
            jpaRepository.findById(cart.getId()).ifPresent(e -> entity.setCreatedAt(e.getCreatedAt()));
        }
        CartEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cart> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId).map(mapper::toDomain);
    }

    @Override
    public List<Cart> findAbandonedBefore(LocalDateTime timestamp) {
        return jpaRepository.findByAbandonedAtIsNullAndUpdatedAtBefore(timestamp).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void delete(Cart cart) {
        jpaRepository.delete(mapper.toEntity(cart));
    }
}