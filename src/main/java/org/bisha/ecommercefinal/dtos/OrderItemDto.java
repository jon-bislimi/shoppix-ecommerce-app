package org.bisha.ecommercefinal.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {

    @NotNull(message = "Order item ID cannot be null")
    @PositiveOrZero(message = "Order item ID must be positive")
    private Long id;

    @NotNull(message = "Order item user cannot be null")
    private @Valid Long userId;

    @NotNull(message = "Product ID cannot be null")
    private @Valid Long productId;

    @NotNull(message = "Order ID cannot be null")
    private @Valid Long orderId;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @Positive(message = "Price must be positive")
    private double price;
}
