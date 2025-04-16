package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.OrderDto;
import org.bisha.ecommercefinal.models.Order;
import org.bisha.ecommercefinal.models.OrderItem;
import org.bisha.ecommercefinal.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper extends SimpleMapper<Order, OrderDto> {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemIds", source = "orderItems")
    OrderDto toDto(Order order);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "orderItems", source = "orderItemIds")
    Order toEntity(OrderDto orderDto);

    default List<Long> mapOrderItemsToIds(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());
    }

    default List<OrderItem> mapIdsToOrderItems(List<Long> orderItemIds) {
        if (orderItemIds == null || orderItemIds.isEmpty()) {
            return Collections.emptyList();
        }
        return orderItemIds.stream()
                .map(orderItemId -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(orderItemId);
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    default Long mapUserToId(User user) {
        return user.getId();
    }

    default User mapIdToUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}


//        modelMapper.typeMap(OrderDto.class, Order.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        User user = new User();
//                        user.setId(src.getUserId());
//                        return user;
//                    }, Order::setUser);
//
//                    mapper.map(src -> src.getOrderItemIds().stream()
//                            .map(id -> {
//                                OrderItem orderItem = new OrderItem();
//                                orderItem.setId(id);
//                                return orderItem;
//                            }).collect(Collectors.toList()), Order::setOrderItems);
//                });
//
//        modelMapper.typeMap(Order.class, OrderDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getUser().getId(), OrderDto::setUserId);
//
//                    mapper.map(src -> src.getOrderItems().stream()
//                            .map(OrderItem::getId)
//                            .collect(Collectors.toList()), OrderDto::setOrderItemIds);
//                });

