package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Long id);

    CategoryDto saveCategory(CategoryDto category);

    CategoryDto deleteCategory(Long id);

    CategoryDto updateCategoryById(CategoryDto category, Long id);

    CategoryDto getCategoryByName(String name);

}
