package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.models.Category;
import org.bisha.ecommercefinal.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper extends SimpleMapper<Subcategory, SubcategoryDto> {
    @Mapping(target = "parentCategoryId", source = "parentCategory")
    SubcategoryDto toDto(Subcategory subcategory);

    @Mapping(target = "parentCategory", source = "parentCategoryId")
    Subcategory toEntity(SubcategoryDto subcategoryDto);

    default Long mapCategoryToId(Category category) {
        return category.getId();
    }

    default Category mapIdToCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }
}











//package org.bisha.ecommerce.mappers;
//
//import org.bisha.ecommerce.dtos.SubcategoryDto;
//import org.bisha.ecommerce.models.Category;
//import org.bisha.ecommerce.models.Subcategory;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.w3c.dom.stylesheets.LinkStyle;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Component
//public class SubcategoryMapper {
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public SubcategoryDto toDto(Subcategory subcategory) {
//        return modelMapper.map(subcategory, SubcategoryDto.class);
//    }
//
//    public Subcategory toEntity(SubcategoryDto subcategoryDto) {
//        return modelMapper.map(subcategoryDto, Subcategory.class);
//    }
//
//    public List<SubcategoryDto> toDtoList(List<Subcategory> subcategories) {
//        return subcategories.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<Subcategory> toEntityList(List<SubcategoryDto> subcategoryDtos) {
//        return subcategoryDtos.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//    }
//
////        modelMapper.typeMap(SubcategoryDto.class, Subcategory.class)
////                .addMappings(mapper -> {
////                    mapper.map(src -> {
////                        Category category = new Category();
////                        category.setId(src.getParentCategoryId());
////                        return category;
////                    }, Subcategory::setParentCategory);
////                });
////
////        modelMapper.typeMap(Subcategory.class, SubcategoryDto.class)
////                .addMappings(mapper -> {
////                    mapper.map(src -> Optional.ofNullable(src.getParentCategory())
////                            .map(Category::getId)
////                            .orElse(null), SubcategoryDto::setParentCategoryId);
////                });
//
//}
