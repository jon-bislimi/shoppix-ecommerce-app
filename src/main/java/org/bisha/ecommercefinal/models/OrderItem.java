package org.bisha.ecommercefinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = "Product cannot be null")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PositiveOrZero(message = "Quantity must be zero or positive")
    @NotNull(message = "Quantity cannot be null")
    @Column(name = "quantity")
    private int quantity;

    @NotNull(message = "Order cannot be null")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @PositiveOrZero(message = "Price must be zero or positive")
    @Column(name = "price")
    private double price;
}