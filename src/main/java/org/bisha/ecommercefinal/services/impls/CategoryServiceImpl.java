package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.CategoryMapper;
import org.bisha.ecommercefinal.repositories.CategoryRepository;
import org.bisha.ecommercefinal.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public CategoryDto saveCategory(CategoryDto category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Category already exists");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(category)));
    }

    @Override
    public CategoryDto deleteCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.deleteById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto updateCategoryById(CategoryDto category, Long id) {
        var existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingCategory.setName(category.getName());
        if (category.getDescription() != null && !category.getDescription().isBlank())
            existingCategory.setDescription(category.getDescription());
        return categoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}