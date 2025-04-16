package org.bisha.ecommercefinal.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.bisha.ecommercefinal.dtos.OrderItemDto;
import org.bisha.ecommercefinal.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/all")
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItemDto getOrderItemById(@PathVariable @PositiveOrZero Long id) {
        return orderItemService.getOrderItemById(id);
    }

    @PostMapping("/add")
    public OrderItemDto createOrderItem(@Valid OrderItemDto orderItemDto) {
        return orderItemService.createOrderItem(orderItemDto);
    }

    @PutMapping("/update/{id}")
    public OrderItemDto updateOrderItem(@PathVariable @PositiveOrZero Long id, @Valid OrderItemDto orderItemDto) {
        return orderItemService.updateOrderItem(id, orderItemDto);
    }

    @DeleteMapping("/delete/{id}")
    public OrderItemDto deleteOrderItem(@PathVariable @PositiveOrZero Long id) {
        return orderItemService.deleteOrderItem(id);
    }
}
