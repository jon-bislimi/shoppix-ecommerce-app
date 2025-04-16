package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.ShoppingCart;
import org.bisha.ecommercefinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);

    boolean existsByUserId(Long userId);

    Optional<ShoppingCart> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<ShoppingCart> findByTotalPriceGreaterThan(double price);
}
