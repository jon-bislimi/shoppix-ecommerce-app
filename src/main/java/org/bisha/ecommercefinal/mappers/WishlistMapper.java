package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.WishlistDto;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.User;
import org.bisha.ecommercefinal.models.Wishlist;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WishlistMapper extends SimpleMapper<Wishlist, WishlistDto> {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productIds", source = "products")
    WishlistDto toDto(Wishlist wishlist);

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "products", source = "productIds")
    Wishlist toEntity(WishlistDto wishlistDto);

    default List<Long> mapProductsToIds(List<Product> products) {
        return products != null ? products.stream()
                .map(Product::getId)
                .collect(Collectors.toList()) : null;
    }

    default List<Product> mapIdsToProducts(List<Long> ids) {
        return ids != null ? ids.stream()
                .map(id -> {
                    Product product = new Product();
                    product.setId(id);
                    return product;
                })
                .collect(Collectors.toList()) : null;
    }

    default Long mapUserToId(User user) {
        return user != null ? user.getId() : null;
    }

    default User mapIdToUser(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }


//        modelMapper.typeMap(WishlistDto.class, Wishlist.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        User user = new User();
//                        user.setId(src.getUserId());
//                        return user;
//                    }, Wishlist::setUser);
//                    mapper.map(src -> Optional.ofNullable(src.getProductIds())
//                            .map(ids -> ids.stream()
//                                    .map(id -> {
//                                        Product product = new Product();
//                                        product.setId(id);
//                                        return product;
//                                    })
//                                    .collect(Collectors.toList()))
//                            .orElse(null), Wishlist::setProducts);
//                });
//
//        modelMapper.typeMap(Wishlist.class, WishlistDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> Optional.ofNullable(src.getUser())
//                            .map(User::getId)
//                            .orElse(null), WishlistDto::setUserId);
//                    mapper.map(src -> Optional.ofNullable(src.getProducts())
//                            .map(products -> products.stream()
//                                    .map(Product::getId)
//                                    .collect(Collectors.toList()))
//                            .orElse(null), WishlistDto::setProductIds);
//                });
}
