package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.OrderItemDto;
import org.bisha.ecommercefinal.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends SimpleMapper<OrderItem, OrderItemDto> {

    @Mapping(target = "userId", source = "user")
    @Mapping(target = "productId", source = "product")
    @Mapping(target = "orderId", source = "order")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "order", source = "orderId")
    OrderItem toEntity(OrderItemDto orderItemDto);


    default Long mapUserToUserId(User user) {
        return user.getId();
    }

    default Long mapProductToProductId(Product product) {
        return product.getId();
    }

    default Long mapOrderToOrderId(Order order) {
        return order.getId();
    }

    default User mapUserIdToUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Product mapProductIdToProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    default Order mapOrderIdToOrder(Long orderId) {
        Order order = new Order();
        order.setId(orderId);
        return order;
    }


//        modelMapper.typeMap(OrderItemDto.class, OrderItem.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        User user = new User();
//                        user.setId(src.getUserId());
//                        return user;
//                    }, OrderItem::setUser);
//
//                    mapper.map(src -> {
//                        Product product = new Product();
//                        product.setId(src.getProductId());
//                        return product;
//                    }, OrderItem::setProduct);
//
//                    mapper.map(src -> {
//                        Order order = new Order();
//                        order.setId(src.getOrderId());
//                        return order;
//                    }, OrderItem::setOrder);
//                });
//
//        modelMapper.typeMap(OrderItem.class, OrderItemDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getUser().getId(), OrderItemDto::setUserId);
//
//                    mapper.map(src -> src.getProduct().getId(), OrderItemDto::setProductId);
//
//                    mapper.map(src -> src.getOrder().getId(), OrderItemDto::setOrderId);
//                });
}
