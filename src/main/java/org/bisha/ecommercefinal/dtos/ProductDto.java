package org.bisha.ecommercefinal.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bisha.ecommercefinal.models.Image;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    @PositiveOrZero(message = "Id must be positive or zero")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private double price;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock must be zero or positive")
    private Integer stock;

    private Long categoryId;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 100, message = "Brand must be less than or equal to 100 characters")
    private String brand;

    @NotNull(message = "Rating is mandatory")
    @DecimalMin(value = "1.0", message = "Rating must be zero or positive")
    @DecimalMax(value = "5.0", message = "Rating must be less than or equal to 5")
    private Double rating;

    private Long subcategoryId;

    @NotNull(message = "Availability status is mandatory")
    private Boolean available;

    @Size(min = 1, message = "There must be at least one image URL")
    private List<String> images;

    private List<ReviewDto> reviews;

    private LocalDate createdAt;
}