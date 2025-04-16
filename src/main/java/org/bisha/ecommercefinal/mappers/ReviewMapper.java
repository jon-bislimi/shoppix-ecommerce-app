package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ReviewDto;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.Review;
import org.bisha.ecommercefinal.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends SimpleMapper<Review, ReviewDto> {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewDto toDto(Review review);

    @Mapping(target = "product", source = "productId")
    @Mapping(target = "user", source = "userId")
    Review toEntity(ReviewDto reviewDto);

    default Long mapProductToProductId(Product product) {
        return product != null ? product.getId() : null;
    }

    default Product mapProductIdToProduct(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    default Long mapUserToUserId(User user) {
        return user != null ? user.getId() : null;
    }

    default User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }
}

//        modelMapper.typeMap(ReviewDto.class, Review.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        Product product = new Product();
//                        product.setId(src.getProductId());
//                        return product;
//                    }, Review::setProduct);
//                    mapper.map(src -> {
//                        User user = new User();
//                        user.setId(src.getUserId());
//                        return user;
//                    }, Review::setUser);
//                });
//
//        modelMapper.typeMap(Review.class, ReviewDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> Optional.ofNullable(src.getProduct())
//                            .map(Product::getId)
//                            .orElse(null), ReviewDto::setProductId);
//                    mapper.map(src -> Optional.ofNullable(src.getUser())
//                            .map(User::getId)
//                            .orElse(null), ReviewDto::setUserId);
//                });
//}

