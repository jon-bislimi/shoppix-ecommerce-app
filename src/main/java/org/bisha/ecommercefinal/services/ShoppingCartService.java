package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto createCart(Long userId);

    ShoppingCartDto addProductToCart(Long userId, ProductDto product);

    ShoppingCartDto removeProductFromCart(Long userId, Long productId);

    ShoppingCartDto getCartByUserId(Long userId);

    ShoppingCartDto clearCart(Long userId);

    double getTotalPrice(Long userId);

    double updateCartTotalPrice(Long userId, double newPrice);

    int getProductCount(Long userId);

    boolean isProductInCart(Long userId, Long productId);

}