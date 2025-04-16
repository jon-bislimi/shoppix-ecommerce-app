package org.bisha.ecommercefinal.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.services.CategoryService;
import org.bisha.ecommercefinal.services.SubcategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public CategoryController(CategoryService categoryService, SubcategoryService subcategoryService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable @Min(1) Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        return (category != null) ? ResponseEntity.ok(category) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody @Valid CategoryDto category) {
        CategoryDto savedCategory = categoryService.saveCategory(category);
        if (savedCategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable @Min(1) Long id) {
        CategoryDto deletedCategory = categoryService.deleteCategory(id);
        return (deletedCategory != null) ? ResponseEntity.ok(deletedCategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@RequestBody @Valid CategoryDto category, @PathVariable @Min(1) Long id) {
        CategoryDto updatedCategory = categoryService.updateCategoryById(category, id);
        return (updatedCategory != null) ? ResponseEntity.ok(updatedCategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable @NotBlank String name) {
        CategoryDto category = categoryService.getCategoryByName(name);
        return (category != null) ? ResponseEntity.ok(category) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/subcategories/all")
    public ResponseEntity<List<SubcategoryDto>> getAllSubcategories() {
        List<SubcategoryDto> subcategories = subcategoryService.getAllSubcategories();
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/subcategories/category-name/{categoryName}")
    public ResponseEntity<List<SubcategoryDto>> getSubcategoriesByCategoryName(@NotBlank @PathVariable String categoryName) {
        List<SubcategoryDto> subcategories = subcategoryService.getSubcategoriesByCategoryName(categoryName);
        return (subcategories != null && !subcategories.isEmpty()) ? ResponseEntity.ok(subcategories) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<SubcategoryDto> getSubcategoryById(@PathVariable @Min(1) Long id) {
        SubcategoryDto subcategory = subcategoryService.getSubcategoryById(id);
        return (subcategory != null) ? ResponseEntity.ok(subcategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/subcategories/add")
    public ResponseEntity<SubcategoryDto> saveSubcategory(@RequestBody @Valid SubcategoryDto subcategory) {
        SubcategoryDto savedSubcategory = subcategoryService.saveSubcategory(subcategory);
        if (savedSubcategory == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(savedSubcategory, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/subcategories/delete/{id}")
    public ResponseEntity<SubcategoryDto> deleteSubcategory(@PathVariable @Min(1) Long id) {
        SubcategoryDto deletedSubcategory = subcategoryService.deleteSubcategory(id);
        return (deletedSubcategory != null) ? ResponseEntity.ok(deletedSubcategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("update/subcategories/update/{id}")
    public ResponseEntity<SubcategoryDto> updateSubcategoryById(@RequestBody @Valid SubcategoryDto subcategory, @PathVariable @Min(1) Long id) {
        SubcategoryDto updatedSubcategory = subcategoryService.updateSubcategoryById(subcategory, id);
        return (updatedSubcategory != null) ? ResponseEntity.ok(updatedSubcategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/subcategories/name/{name}")
    public ResponseEntity<SubcategoryDto> getSubcategoryByName(@PathVariable @NotBlank String name) {
        SubcategoryDto subcategory = subcategoryService.getSubcategoryByName(name);
        return (subcategory != null) ? ResponseEntity.ok(subcategory) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get-dto")
    public ResponseEntity<CategoryDto> getDto() {
        return ResponseEntity.ok(new CategoryDto());
    }

    @GetMapping("/subcategories/get-dto")
    public ResponseEntity<SubcategoryDto> getSubcategoryDto() {
        return ResponseEntity.ok(new SubcategoryDto());
    }

}
