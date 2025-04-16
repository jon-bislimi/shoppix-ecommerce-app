package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.Category;
import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByNameContaining(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByCategory(Category category);

    List<Product> findByBrand(String brand);

    List<Product> findByRatingGreaterThanEqual(double rating);

    List<Product> findByAvailable(boolean isAvailable);

    List<Product> findByCreatedAtAfter(LocalDate date);

    List<Product> findByStockGreaterThan(int stock);

    List<Product> findBySubcategory(Subcategory subcategory);
}