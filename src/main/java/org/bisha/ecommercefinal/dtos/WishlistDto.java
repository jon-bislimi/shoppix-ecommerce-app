package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistDto {

    @NotNull(message = "User cannot be null")
    private long userId;

    private List<Long> productIds;
}