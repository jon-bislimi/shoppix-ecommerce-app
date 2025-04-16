package org.bisha.ecommercefinal.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommercefinal.dtos.OrderDto;
import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        if (orderDto.getOrderItemIds() == null) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @NotNull @Min(0) Long id) {
        OrderDto order = orderService.getOrderById(id);
        return (order != null) ? ResponseEntity.ok(order) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable @NotNull @Min(0) Long id, @RequestBody @Valid OrderDto orderDto) {
        if (orderDto.getOrderItemIds() == null) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<OrderDto> cancelOrderById(@PathVariable @NotNull @Min(0) Long id) {
        OrderDto canceledOrder = orderService.cancelOrderById(id);
        return (canceledOrder != null) ? ResponseEntity.ok(canceledOrder) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        List<OrderDto> userOrders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(userOrders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable @NotNull OrderStatus status) {
        List<OrderDto> ordersByStatus = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ordersByStatus);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable @NotNull @Min(0) Long id, @RequestBody @NotNull OrderStatus status) {
        OrderDto updatedStatusOrder = orderService.updateOrderStatus(id, status);

        return updatedStatusOrder != null ? ResponseEntity.ok(updatedStatusOrder) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDto>> getOrdersByDateRange(
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<OrderDto> ordersByDateRange = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(ordersByDateRange);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<OrderDto>> getOrdersByTotalPriceRange(
            @RequestParam @NotNull @Min(0) double minPrice,
            @RequestParam @NotNull @Min(0) double maxPrice) {
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Min price must be less than max price");
        }
        List<OrderDto> ordersByPriceRange = orderService.getOrdersByTotalPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(ordersByPriceRange);
    }

    @PutMapping("/{id}/address-phone")
    public ResponseEntity<OrderDto> updateOrderAddressAndPhone(
            @PathVariable @NotNull @Min(0) Long id,
            @RequestParam @NotNull String address,
            @RequestParam @NotNull String phone) {
        OrderDto updatedOrder = orderService.updateOrderAddressAndPhone(id, address, phone);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get-dto")
    public ResponseEntity<OrderDto> getDto() {
        return ResponseEntity.ok(new OrderDto());
    }
}
