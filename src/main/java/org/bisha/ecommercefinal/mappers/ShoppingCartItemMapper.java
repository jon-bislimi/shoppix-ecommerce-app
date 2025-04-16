package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ShoppingCartItemDto;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.ShoppingCart;
import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShoppingCartItemMapper extends SimpleMapper<ShoppingCartItem, ShoppingCartItemDto> {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "shoppingCartId", source = "shoppingCart.id")
    ShoppingCartItemDto toDto(ShoppingCartItem shoppingCartItem);

    @Mapping(target = "product", source = "productId")
    @Mapping(target = "shoppingCart", source = "shoppingCartId")
    ShoppingCartItem toEntity(ShoppingCartItemDto shoppingCartItemDto);

    default Long mapProductToId(Product product) {
        return product != null ? product.getId() : null;
    }

    default Product mapIdToProduct(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Long mapShoppingCartToId(ShoppingCart shoppingCart) {
        return shoppingCart != null ? shoppingCart.getId() : null;
    }

    default ShoppingCart mapIdToShoppingCart(Long id) {
        if (id == null) {
            return null;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
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
//                            .orElse(null), ShoppingCartDto::setUserId);
//                    mapper.map(src -> Optional.ofNullable(src.getItems())
//                            .map(items -> items.stream()
//                                    .map(ShoppingCartItem::getId)
//                                    .collect(Collectors.toList()))
//                            .orElse(null), ShoppingCartDto::setShoppingCartItemIds);
//                });
}