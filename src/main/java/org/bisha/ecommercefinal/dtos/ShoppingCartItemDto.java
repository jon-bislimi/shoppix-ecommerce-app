package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartItemDto {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Shopping cart cannot be null")
    private Long shoppingCartId;

    @Positive(message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private double price;
}