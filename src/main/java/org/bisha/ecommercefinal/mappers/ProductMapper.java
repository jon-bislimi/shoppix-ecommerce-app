package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.models.Category;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper extends SimpleMapper<Product, ProductDto> {

    // Entity → DTO
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "subcategoryId", source = "subcategory.id")
    @Mapping(target = "images", source = "imageURLs") // Auto-mapped via ImageMapper
    ProductDto toDto(Product product);

    // DTO → Entity
    @Mapping(target = "category", source = "categoryId")
    @Mapping(target = "subcategory", source = "subcategoryId")
    @Mapping(target = "imageURLs", source = "images") // Auto-mapped via ImageMapper
    Product toEntity(ProductDto productDto);

    // Helper methods for category/subcategory mapping
    default Category mapIdToCategory(Long categoryId) {
        if (categoryId == null) return null;
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }

    default Subcategory mapIdToSubcategory(Long subcategoryId) {
        if (subcategoryId == null) return null;
        Subcategory subcategory = new Subcategory();
        subcategory.setId(subcategoryId);
        return subcategory;
    }
}
//        modelMapper.typeMap(ProductDto.class, Product.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> {
//                        Category category = new Category();
//                        category.setId(src.getCategoryId());
//                        return category;
//                    }, Product::setCategory);
//                    mapper.map(src -> {
//                        Subcategory subcategory = new Subcategory();
//                        subcategory.setId(src.getSubcategoryId());
//                        return subcategory;
//                    }, Product::setSubcategory);
//                });
//
//        modelMapper.typeMap(Product.class, ProductDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> Optional.ofNullable(src.getCategory())
//                            .map(Category::getId)
//                            .orElse(null), ProductDto::setCategoryId);
//                    mapper.map(src -> Optional.ofNullable(src.getSubcategory())
//                            .map(Subcategory::getId)
//                            .orElse(null), ProductDto::setSubcategoryId);
//                });


