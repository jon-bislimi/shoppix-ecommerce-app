package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDto {
    @NotNull(message = "User cannot be null")
    private long userId;

    @NotEmpty(message = "Items list cannot be empty")
    private List<Long> ShoppingCartItemIds;

    @PositiveOrZero(message = "Total price must be zero or positive")
    @Digits(integer = 10, fraction = 2, message = "Total price must be a valid monetary amount")
    private double totalPrice;

    @NotNull(message = "Creation date is mandatory")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDateTime createdAt;

    @NotNull(message = "Updated date is mandatory")
    @PastOrPresent(message = "Updated date must be in the past or present")
    private LocalDateTime updatedAt;
}