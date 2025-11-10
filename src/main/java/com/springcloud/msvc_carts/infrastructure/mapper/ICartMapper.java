package com.springcloud.msvc_carts.infrastructure.mapper;

import com.springcloud.msvc_carts.domain.model.Cart;
import com.springcloud.msvc_carts.domain.model.CartItem;
import com.springcloud.msvc_carts.infrastructure.persistence.entity.CartEntity;
import com.springcloud.msvc_carts.infrastructure.persistence.entity.CartItemEntity;
import com.springcloud.msvc_carts.infrastructure.web.dto.request.CartItemRequest;
import com.springcloud.msvc_carts.infrastructure.web.dto.response.CartResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICartMapper {

    // Domain ↔ Persistence
    CartEntity toEntity(Cart domain);
    Cart toDomain(CartEntity entity);

    CartItemEntity toItemEntity(CartItem domain);
    CartItem toItemDomain(CartItemEntity entity);

    //  Web ↔ Domain
    CartResponse toResponse(Cart domain);

    //  Request DTO → Domain Item
    @Mapping(target = "id", ignore = true)
    CartItem toDomainItem(CartItemRequest request);
}