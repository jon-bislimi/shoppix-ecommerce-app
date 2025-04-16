package org.bisha.ecommercefinal.services;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.WishlistDto;

import java.util.List;

public interface WishlistService {
    WishlistDto createWishlist(Long userId);

    WishlistDto getWishlistByUserId(Long userId);

    ProductDto addProductToWishlist(Long userId, Long productId);

    ProductDto removeProductFromWishlist(Long userId, Long productId);

    WishlistDto clearWishlist(Long userId);

    List<ProductDto> getAllProductsInWishlist(Long userId);

    boolean isProductInWishlist(Long userId, Long productId);

    WishlistDto duplicateWishlist(Long wishlistId, Long userId);

    WishlistDto getWishlistById(Long wishlistId);
}
