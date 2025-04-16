package org.bisha.ecommercefinal.configs;

import org.bisha.ecommercefinal.mappers.*;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public UserMapper userMapper() {
//        return new UserMapper();
//    }
//    @Bean
//    public ProductMapper productMapper() {
//        return new ProductMapper();
//    }
//    @Bean
//    public SubcategoryMapper subcategoryMapper() {
//        return new SubcategoryMapper();
//    }
    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }
//    @Bean
//    public ImageMapper imageMapper() {
//        return new ImageMapper();
//    }
//    @Bean
//    public OrderItemMapper orderItemMapper() {
//        return new OrderItemMapper();
//    }
//    @Bean
//    public OrderMapper orderMapper() {
//        return new OrderMapper();
//    }
//    @Bean
//    public ReviewMapper reviewMapper() {
//        return new ReviewMapper();
//    }
//    @Bean
//    public ShoppingCartMapper shoppingCartMapper() {
//        return new ShoppingCartMapper();
//    }
//    @Bean
//    public ShoppingCartItemMapper shoppingCartItemMapper() {
//        return new ShoppingCartItemMapper();
//    }
//    @Bean
//    public WishlistMapper wishlistMapper() {
//        return new WishlistMapper();
//    }

}
