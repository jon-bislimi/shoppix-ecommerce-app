//package org.bisha.ecommercefinal.mappers;
//
//import org.bisha.ecommercefinal.dtos.ImageDto;
//import org.bisha.ecommercefinal.dtos.ProductDto;
//import org.bisha.ecommercefinal.models.Image;
//import org.bisha.ecommercefinal.models.Product;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface ImageMapper extends SimpleMapper<Image, ImageDto> {
//
//    @Mapping(target = "productId", source = "product.id")
//    ImageDto toDto(Image image);
//
//    @Mapping(target = "product", source = "productId")
//    Image toEntity(ImageDto imageDto);
//
//    // Automatically handles null inputs
//    default Long mapProductToProductId(Product product) {
//        return (product != null) ? product.getId() : null;
//    }
//
//    default Product mapProductIdToProduct(Long productId) {
//        if (productId == null) return null;
//        Product product = new Product();
//        product.setId(productId);
//        return product;
//    }
//}
