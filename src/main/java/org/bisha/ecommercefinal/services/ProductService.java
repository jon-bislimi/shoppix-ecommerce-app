package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.dtos.ImageDto;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.models.Image;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts();

    ProductDto saveProduct(ProductDto productDto);

    ProductDto addProduct(ProductDto productDto);

    ProductDto deleteProductById(Long id);

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getProductsByCategory(CategoryDto categoryDto);

    List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByRatingGreaterThanEqual(double rating);

    List<ProductDto> getProductsByAvailability(boolean isAvailable);

    List<ProductDto> getProductsCreatedAfter(LocalDate date);

    List<ProductDto> getProductsWithStockGreaterThan(int stock);

    List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto);

    ProductDto updateProductById(Long id, ProductDto productDto);

    ProductDto updateProductByIdTemp(Long id, ProductDto productDto);

    ProductDto addImageToProduct(Long productId, String imageUrl);
}