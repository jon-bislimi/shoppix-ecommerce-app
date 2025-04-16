package org.bisha.ecommercefinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than or equal to 100 characters")
    @Column(name = "name")
    private String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    @Column(name = "description")
    private String description;

    @Positive(message = "Price must be positive")
    @Column(name = "price")
    private double price;

    @ElementCollection
    private List<String> imageURLs = List.of();

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @PositiveOrZero(message = "Stock must be zero or positive")
    @Column(name = "stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 100, message = "Brand must be less than or equal to 100 characters")
    @Column(name = "brand")
    private String brand;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be zero or positive")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be less than or equal to 5")
    @Column(name = "rating")
    private double rating;

    @ManyToOne
    private Subcategory subcategory;

    @Column(name = "is_available")
    private boolean available;

    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date must be in the past or present")
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;
}