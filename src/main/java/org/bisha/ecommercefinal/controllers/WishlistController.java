package org.bisha.ecommercefinal.controllers;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.WishlistDto;
import org.bisha.ecommercefinal.services.UserService;
import org.bisha.ecommercefinal.services.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
@Validated
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistController(WishlistService wishlistService, UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<WishlistDto> createWishlist(@PathVariable @NotNull @Min(1) Long userId) {
        WishlistDto createdWishlist = wishlistService.createWishlist(userId);
        return new ResponseEntity<>(createdWishlist, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<WishlistDto> getWishlistByUserId(@PathVariable @NotNull @Min(1) Long userId) {
        WishlistDto wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/{userId}/add-product/{productId}")
    public ResponseEntity<ProductDto> addProductToWishlist(@PathVariable @NotNull @Min(1) Long userId,
                                                           @PathVariable @NotNull @Min(1) Long productId) {
        ProductDto addedProduct = wishlistService.addProductToWishlist(userId, productId);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/remove-product/{productId}")
    public ResponseEntity<ProductDto> removeProductFromWishlist(@PathVariable @NotNull @Min(1) Long userId,
                                                                @PathVariable @NotNull @Min(1) Long productId) {
        ProductDto removedProduct = wishlistService.removeProductFromWishlist(userId, productId);
        return ResponseEntity.ok(removedProduct);
    }

    @DeleteMapping("/{wishlistId}/clear")
    public ResponseEntity<WishlistDto> clearWishlist(@PathVariable @NotNull @Min(1) Long wishlistId) {
        WishlistDto clearedWishlist = wishlistService.clearWishlist(wishlistId);
        return ResponseEntity.ok(clearedWishlist);
    }

    @GetMapping("/{wishlistId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsInWishlist(@PathVariable @NotNull @Min(1) Long wishlistId) {
        List<ProductDto> products = wishlistService.getAllProductsInWishlist(wishlistId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{userId}/contains/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(@PathVariable @NotNull @Min(1) Long userId,
                                                       @PathVariable @NotNull @Min(1) Long productId) {
        boolean isInWishlist = wishlistService.isProductInWishlist(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    @PostMapping("/{wishlistId}/duplicate/{userId}")
    public ResponseEntity<WishlistDto> duplicateWishlist(@PathVariable @NotNull @Min(1) Long wishlistId,
                                                         @PathVariable @NotNull @Min(1) Long userId) {
        WishlistDto duplicatedWishlist = wishlistService.duplicateWishlist(wishlistId, userId);
        return new ResponseEntity<>(duplicatedWishlist, HttpStatus.CREATED);
    }

    @PostMapping("/{wishlistId}/buy")
    public ResponseEntity<WishlistDto> buyWishlistProducts(@PathVariable @NotNull @Min(0) Long wishlistId) {
        HashMap<Long, Integer> productIdsAndQuantities = new HashMap<>();
        List<ProductDto> products = getAllProductsInWishlist(wishlistId).getBody();
        for (ProductDto product : products) {
            productIdsAndQuantities.put(product.getId(), 1);
        }
        var user = userService.getUserById(wishlistService.getWishlistById(wishlistId).getUserId());
        userService.buyProducts(user.getId(), productIdsAndQuantities);
        clearWishlist(wishlistId);
        return getWishlistByUserId(user.getId());
    }
}
