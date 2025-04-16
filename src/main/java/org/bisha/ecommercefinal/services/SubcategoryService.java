package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.SubcategoryDto;

import java.util.List;

public interface SubcategoryService {
    List<SubcategoryDto> getAllSubcategories();

    List<SubcategoryDto> getSubcategoriesByCategoryName(String categoryName);

    SubcategoryDto getSubcategoryById(Long id);

    SubcategoryDto saveSubcategory(SubcategoryDto subcategory);

    SubcategoryDto deleteSubcategory(Long id);

    SubcategoryDto updateSubcategoryById(SubcategoryDto subcategory, Long id);

    SubcategoryDto getSubcategoryByName(String name);
}
