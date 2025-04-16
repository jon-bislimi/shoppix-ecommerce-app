package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.OrderItemDto;

import java.util.List;

public interface OrderItemService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);

    OrderItemDto getOrderItemById(Long id);

    List<OrderItemDto> getAllOrderItems();

    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);

    OrderItemDto deleteOrderItem(Long id);
}