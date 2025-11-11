package com.springcloud.msvc_carts.domain.port.in;

import com.springcloud.msvc_carts.domain.model.Cart;
import java.time.LocalDateTime;
import java.util.List;

public interface IAbandonedCartUseCase {
    List<Cart> findAbandonedCarts(LocalDateTime threshold);
}
