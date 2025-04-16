package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.CategoryDto;
import org.bisha.ecommercefinal.dtos.ImageDto;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.SubcategoryDto;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.*;
import org.bisha.ecommercefinal.models.Image;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.Subcategory;
import org.bisha.ecommercefinal.repositories.CategoryRepository;
import org.bisha.ecommercefinal.repositories.ImageRepository;
import org.bisha.ecommercefinal.repositories.ProductRepository;
import org.bisha.ecommercefinal.repositories.SubcategoryRepository;
import org.bisha.ecommercefinal.services.ProductService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryRepository categoryRepository;
    private final ReviewMapper reviewMapper;
    private final SubcategoryRepository subcategoryRepository;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProductServiceImpl.class);


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryMapper categoryMapper, SubcategoryMapper subcategoryMapper, CategoryRepository categoryRepository, ReviewMapper reviewMapper, SubcategoryRepository subcategoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryRepository = categoryRepository;
        this.reviewMapper = reviewMapper;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto addProduct(ProductDto productDto) {
        List<Product> products = productRepository.findByNameContaining(productDto.getName());
        var existingProduct = products.stream()
                .filter(product -> product.getName().equals(productDto.getName()) &&
                        product.getBrand().equals(productDto.getBrand()) &&
                        product.getCategory().getId().equals(productDto.getCategoryId()))
                .findFirst();

        if (existingProduct.isEmpty()) {
            productDto.setStock(1);
            return saveProduct(productDto);
        } else {
            var product = existingProduct.get();
            product.setStock(product.getStock() + 1);
            return productMapper.toDto(productRepository.save(product));
        }
    }

    @Override
    public ProductDto deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getStock() > 1) {
            product.setStock(product.getStock() - 1);
        }
        productRepository.deleteById(id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productMapper.toDtoList(productRepository.findByNameContaining(name));
    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }
        var category = categoryMapper.toEntity(categoryDto);
        return productMapper.toDtoList(productRepository.findByCategory(category));
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productMapper.toDtoList(productRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productMapper.toDtoList(productRepository.findByBrand(brand));
    }

    @Override
    public List<ProductDto> getProductsByRatingGreaterThanEqual(double rating) {
        return productMapper.toDtoList(productRepository.findByRatingGreaterThanEqual(rating));
    }

    @Override
    public List<ProductDto> getProductsByAvailability(boolean isAvailable) {
        return productMapper.toDtoList(productRepository.findByAvailable(isAvailable));
    }

    @Override
    public List<ProductDto> getProductsCreatedAfter(LocalDate date) {
        return productMapper.toDtoList(productRepository.findByCreatedAtAfter(date));
    }

    @Override
    public List<ProductDto> getProductsWithStockGreaterThan(int stock) {
        return productMapper.toDtoList(productRepository.findByStockGreaterThan(stock));
    }

    @Override
    public List<ProductDto> getProductsBySubcategory(SubcategoryDto subcategoryDto) {
        if (subcategoryRepository.findByName(subcategoryDto.getName()).isEmpty()) {
            throw new IllegalArgumentException("Subcategory not found");
        }
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        return productMapper.toDtoList(productRepository.findBySubcategory(subcategory));
    }

    @Override
    public ProductDto updateProductById(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        logger.info("Updating product with id: {}", id);
        product.setName(productDto.getName() != null ? productDto.getName() : "Default Name");
        product.setDescription(productDto.getDescription() != null ? productDto.getDescription() : "Default Description");
        product.setPrice(productDto.getPrice());
        product.setImageURLs(productDto.getImages() != null ? productDto.getImages() : List.of());
        if (productDto.getReviews() == null) {
            product.setReviews(List.of());
        } else {
            product.setReviews(reviewMapper.toEntityList(productDto.getReviews()));
        }
        product.setStock(productDto.getStock() != null ? productDto.getStock() : 0);
        logger.info("somewhere in the middle");
        Long categoryId = productDto.getCategoryId();
        if (categoryId == null) {
            product.setCategory(categoryRepository.findById(1L)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        } else {
            product.setCategory(categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }
        logger.info("Ja ku eshte errori");
        product.setBrand(productDto.getBrand() != null ? productDto.getBrand() : "Default Brand");
        product.setRating(productDto.getRating() != null ? productDto.getRating() : 0.0);
        Long subcategoryId = productDto.getSubcategoryId();
        logger.info("Subcategory ID: {}", subcategoryId);
        if (subcategoryId == null) {
            product.setSubcategory(subcategoryRepository.findById(1L)
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found")));
        } else {
            product.setSubcategory(subcategoryRepository.findById(subcategoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found")));
        }
        logger.info("Subcategory set successfully");
        logger.info("qitu a");
        product.setAvailable(productDto.getAvailable() != null ? productDto.getAvailable() : false);
        product.setCreatedAt(productDto.getCreatedAt() != null ? productDto.getCreatedAt() : LocalDate.now());
        logger.info("Product updated");
        Product testProduct;
        try {
            testProduct = productRepository.save(product);
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new RuntimeException("Error updating product", e);
        }
        return productMapper.toDto(testProduct);

    }

    @Override
    public ProductDto updateProductByIdTemp(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//        logger.info("Updating product with id: {}", id);
//        product.setName(productDto.getName());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setImageURLs(productDto.getImages());
//        if (productDto.getReviews() == null) {
//            product.setReviews(List.of());
//        }else {
//            product.setReviews(reviewMapper.toEntityList(productDto.getReviews()));
//        }
        product.setStock(productDto.getStock());
//        logger.info("somewhere in the middle");
//        Long categoryId = productDto.getCategoryId();
//        if (categoryId == null) {
//            product.setCategory(categoryRepository.findById(1L)
//                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
//        }else {
//            product.setCategory(categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
//        }
//        logger.info("Ja ku eshte errori");
//        product.setBrand(productDto.getBrand());
//        product.setRating(productDto.getRating());
//        Long subcategoryId = productDto.getSubcategoryId();
//        logger.info("Subcategory ID: {}", subcategoryId);
//        if (subcategoryId == null) {
//            product.setSubcategory(subcategoryRepository.findById(1L)
//                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found")));
//        }else {
//            product.setSubcategory(subcategoryRepository.findById(subcategoryId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found")));
//        }
//        logger.info("Subcategory set successfully");
//        logger.info("qitu a");
//        product.setAvailable(productDto.getAvailable());
//        product.setCreatedAt(productDto.getCreatedAt());
//        logger.info("Product updated");
        try {
            return productMapper.toDto(productRepository.save(product));
        } catch (Exception e) {
            logger.error("Error updating product", e);
            throw new RuntimeException("Error updating product", e);
        }
    }

    @Override
    public ProductDto addImageToProduct(Long productId, String imageUrl) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        System.out.println("adding image: "+ imageUrl);
        System.out.println("images:" + product.getImageURLs());
        product.getImageURLs().add(imageUrl);
        System.out.println("added image: "+ imageUrl);
        productRepository.save(product);
        return productMapper.toDto(product);
    }
}