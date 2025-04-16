package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ShoppingCartDto;
import org.bisha.ecommercefinal.models.ShoppingCart;
import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.bisha.ecommercefinal.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper extends SimpleMapper<ShoppingCart, ShoppingCartDto> {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "shoppingCartItemIds", source = "items")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "items", source = "shoppingCartItemIds")
    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    default Long mapUserToUserId(User user) {
        return user != null ? user.getId() : null;
    }

    default List<Long> mapItemsToShoppingCartItemIds(List<ShoppingCartItem> items) {
        return items != null ? items.stream()
                .map(ShoppingCartItem::getId)
                .collect(Collectors.toList()) : null;
    }

    default User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    default List<ShoppingCartItem> mapShoppingCartItemIdsToItems(List<Long> shoppingCartItemIds) {
        return shoppingCartItemIds != null ? shoppingCartItemIds.stream()
                .map(id -> {
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setId(id);
                    return item;
                })
                .collect(Collectors.toList()) : null;
    }


//        modelMapper.typeMap(ShoppingCartDto.class, ShoppingCart.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        User user = new User();
//                        user.setId(src.getUserId());
//                        return user;
//                    }, ShoppingCart::setUser);
//                    mapper.map(src -> Optional.ofNullable(src.getShoppingCartItemIds())
//                            .map(ids -> ids.stream()
//                                    .map(id -> {
//                                        ShoppingCartItem item = new ShoppingCartItem();
//                                        item.setId(id);
//                                        return item;
//                                    })
//                                    .collect(Collectors.toList()))
//                            .orElse(null), ShoppingCart::setItems);
//                });
//
//        modelMapper.typeMap(ShoppingCart.class, ShoppingCartDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> Optional.ofNullable(src.getUser())
//                            .map(User::getId)
//                            .orElse(null), ShoppingCartDto::setUserId); // Safeguard for null User
//                    mapper.map(src -> Optional.ofNullable(src.getItems())
//                            .map(items -> items.stream()
//                                    .map(ShoppingCartItem::getId)
//                                    .collect(Collectors.toList()))
//                            .orElse(null), ShoppingCartDto::setShoppingCartItemIds);
//                });

}
