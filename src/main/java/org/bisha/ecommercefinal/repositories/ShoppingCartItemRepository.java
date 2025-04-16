package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
    List<ShoppingCartItem> findByShoppingCartId(Long shoppingCartId);

    ShoppingCartItem findByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    List<ShoppingCartItem> deleteByShoppingCartId(Long shoppingCartId);

    ShoppingCartItem deleteByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

    List<ShoppingCartItem> findByShoppingCartIdAndQuantityGreaterThan(Long shoppingCartId, int quantity);
}