package com.springcloud.msvc_carts.infrastructure.web.dto.response;

public record CheckoutConfirmationResponse(
        Long orderId,
        String confirmationMessage
) {}
