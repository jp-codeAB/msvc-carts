package com.springcloud.msvc_carts.infrastructure.web.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemUpdateRequest {
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Minimum quantity is 1")
    private Integer quantity;
}