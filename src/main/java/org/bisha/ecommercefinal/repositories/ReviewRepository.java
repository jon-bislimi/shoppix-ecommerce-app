package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.Product;
import org.bisha.ecommercefinal.models.Review;
import org.bisha.ecommercefinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);

    List<Review> findByUser(User user);

    Optional<Review> findByUserAndProduct(User user, Product product);

    List<Review> findByCreatedAtAfter(LocalDateTime createdAt);

    List<Review> findByCreatedAtBefore(LocalDateTime createdAt);
}