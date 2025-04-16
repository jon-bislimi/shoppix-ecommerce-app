package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    @NotBlank(message = "Url cannot be empty")
    @Size(max = 255, message = "Url must be less than or equal to 255 characters")
    private String url;

    @NotNull(message = "Product cannot be null")
    private long productId;

}