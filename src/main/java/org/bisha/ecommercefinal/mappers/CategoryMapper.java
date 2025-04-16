package org.bisha.ecommercefinal.mappers;


import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.models.Category;
import org.bisha.ecommercefinal.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends SimpleMapper<Category, CategoryDto> {
    @Mapping(target = "subcategoryIds", source = "subcategories")
    CategoryDto toDto(Category category);

    @Mapping(target = "subcategories", source = "subcategoryIds")
    Category toEntity(CategoryDto categoryDto);

    default List<Long> mapSubcategoriesToIds(List<Subcategory> subcategories) {
        if (subcategories == null) {
            return Collections.emptyList();
        }
        return subcategories.stream()
                .map(Subcategory::getId)
                .collect(Collectors.toList());
    }

    default List<Subcategory> mapIdsToSubcategories(List<Long> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return ids.stream()
                .map(id -> {
                    Subcategory subcategory = new Subcategory();
                    subcategory.setId(id);
                    return subcategory;
                })
                .collect(Collectors.toList());
    }
}
















//@Component
//public class CategoryMapper {

//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public CategoryMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//        configureMappings();
//    }
//
//    private void configureMappings() {
//        modelMapper.typeMap(Category.class, CategoryDto.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getSubcategories().stream()
//                            .map(Subcategory::getId)
//                            .collect(Collectors.toList()), CategoryDto::setSubcategoryIds);
//                });
//
//        modelMapper.typeMap(CategoryDto.class, Category.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> src.getSubcategoryIds().stream()
//                            .map(id -> {
//                                Subcategory subcategory = new Subcategory();
//                                subcategory.setId(id);
//                                return subcategory;
//                            })
//                            .collect(Collectors.toList()), Category::setSubcategories);
//                });
//    }
//
//    public CategoryDto toDto(Category category) {
//        return modelMapper.map(category, CategoryDto.class);
//    }
//
//    public Category toEntity(CategoryDto categoryDto) {
//        return modelMapper.map(categoryDto, Category.class);
//    }
//
//    public List<CategoryDto> toDtoList(List<Category> categories) {
//        return categories.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<Category> toEntityList(List<CategoryDto> categoryDtos) {
//        return categoryDtos.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//    }
//}