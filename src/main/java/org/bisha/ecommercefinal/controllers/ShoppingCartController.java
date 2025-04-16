package org.bisha.ecommercefinal.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.ShoppingCartDto;
import org.bisha.ecommercefinal.dtos.ShoppingCartItemDto;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.services.ProductService;
import org.bisha.ecommercefinal.services.ShoppingCartItemService;
import org.bisha.ecommercefinal.services.ShoppingCartService;
import org.bisha.ecommercefinal.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
@Validated
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartItemService shoppingCartItemService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ShoppingCartItemService shoppingCartItemService, UserService userService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartItemService = shoppingCartItemService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/{userId}/add-product/{productId}")
    public ResponseEntity<ShoppingCartDto> addProductToCart(@PathVariable @NotNull @Min(0) Long userId, @PathVariable @NotNull @Min(0) Long productId) {
        System.out.println("Adding product to cart for user id " + userId + " and product id " + productId);
        ProductDto productDto = productService.getProductById(productId);
        ShoppingCartDto updatedCart = shoppingCartService.addProductToCart(userId, productDto);
        return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/remove-product/{productId}")
    public ResponseEntity<ShoppingCartDto> removeProductFromCart(@PathVariable @NotNull @Min(0) Long userId, @PathVariable @NotNull @Min(0) Long productId) {
        ShoppingCartDto updatedCart = shoppingCartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDto> getCartByUserId(@PathVariable @NotNull @Min(0) Long userId) {
        ShoppingCartDto cart = shoppingCartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ShoppingCartDto> clearCart(@PathVariable @NotNull @Min(0) Long userId) {
        ShoppingCartDto clearedCart = shoppingCartService.clearCart(userId);
        return ResponseEntity.ok(clearedCart);
    }

    @GetMapping("/{userId}/total-price")
    public ResponseEntity<Double> getTotalPrice(@PathVariable @NotNull @Min(0) Long userId) {
        double totalPrice = shoppingCartService.getTotalPrice(userId);
        return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/{userId}/update-total-price")
    public ResponseEntity<Double> updateCartTotalPrice(@PathVariable @NotNull @Min(0) Long userId, @RequestParam @NotNull @Min(0) double newPrice) {
        double totalPrice = shoppingCartService.updateCartTotalPrice(userId, newPrice);
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/{userId}/product-count")
    public ResponseEntity<Integer> getProductCount(@PathVariable @NotNull @Min(0) Long userId) {
        int productCount = shoppingCartService.getProductCount(userId);
        return ResponseEntity.ok(productCount);
    }

    @GetMapping("/{userId}/is-product-in-cart/{productId}")
    public ResponseEntity<Boolean> isProductInCart(@PathVariable @NotNull @Min(0) Long userId, @PathVariable @NotNull @Min(0) Long productId) {
        boolean isInCart = shoppingCartService.isProductInCart(userId, productId);
        return ResponseEntity.ok(isInCart);
    }

    @GetMapping("/{shoppingCartId}/items")
    public ResponseEntity<List<ShoppingCartItemDto>> getItemsByShoppingCartId(@PathVariable @NotNull @Min(0) Long shoppingCartId) {
        List<ShoppingCartItemDto> items = shoppingCartItemService.getItemsByShoppingCartId(shoppingCartId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{shoppingCartId}/item/{productId}")
    public ResponseEntity<ShoppingCartItemDto> getItemByShoppingCartIdAndProductId(@PathVariable @NotNull @Min(0) Long shoppingCartId, @PathVariable @NotNull @Min(0) Long productId) {
        ShoppingCartItemDto item = shoppingCartItemService.getItemByShoppingCartIdAndProductId(shoppingCartId, productId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{shoppingCartId}/items/quantity-greater-than/{quantity}")
    public ResponseEntity<List<ShoppingCartItemDto>> getItemsByShoppingCartIdAndQuantityGreaterThan(@PathVariable @NotNull @Min(0) Long shoppingCartId, @PathVariable @NotNull @Min(0) int quantity) {
        List<ShoppingCartItemDto> items = shoppingCartItemService.getItemsByShoppingCartIdAndQuantityGreaterThan(shoppingCartId, quantity);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{shoppingCartId}/add-item")
    public ResponseEntity<ShoppingCartItemDto> addItemToShoppingCart(@PathVariable @NotNull @Min(0) Long shoppingCartId, @RequestBody @Valid ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartItemDto addedItem = shoppingCartItemService.addItemToShoppingCart(shoppingCartId, shoppingCartItemDto);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{shoppingCartId}/update-item")
    public ResponseEntity<ShoppingCartItemDto> updateItemInShoppingCart(@PathVariable @NotNull @Min(0) Long shoppingCartId, @RequestBody @Valid ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCartItemDto updatedItem = shoppingCartItemService.updateItemInShoppingCart(shoppingCartId, shoppingCartItemDto);
        return ResponseEntity.ok(updatedItem);
    }

    @GetMapping("/get-dto")
    public ResponseEntity<ShoppingCartDto> getDto() {
        return ResponseEntity.ok(new ShoppingCartDto());
    }

    @PostMapping("/{userId}/buyAll")
    public ResponseEntity<ShoppingCartDto> buyCartProducts(@PathVariable @NotNull @Min(0) Long userId, @RequestBody List<Integer> quantities) {
        Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

        logger.info("Received request to buy all products for user ID: {}", userId);

        if (userService.getUserById(userId).getRole().equals(Role.ROLE_ADMIN)) {
            logger.error("Admins cannot buy products. User ID: {}", userId);
            throw new IllegalArgumentException("Admins cannot buy products");
        }

        var shoppingCart = shoppingCartService.getCartByUserId(userId);
        logger.info("Retrieved shopping cart for user ID: {}", userId);

        var user = userService.getUserById(userId);
        logger.info("Retrieved user details for user ID: {}", userId);

        HashMap<Long, Integer> productIdsAndQuantities = new HashMap<>();
        int i = 0;
        var productIds = shoppingCart.getShoppingCartItemIds().stream()
                .map(shoppingCartItemService::getShoppingCartItemById)
                .map(ShoppingCartItemDto::getProductId)
                .toList();
        for (Long productId : productIds) {
            productIdsAndQuantities.put(productId, quantities.get(i));
            logger.info("Mapped product ID: {} to quantity: {}", productId, quantities.get(i));
            i++;
        }

        try {
            logger.info("Attempting to buy products for user ID: {}", userId);
            userService.buyProducts(user.getId(), productIdsAndQuantities);
            logger.info("Products bought successfully for user ID: {}", userId);

            logger.info("Clearing shopping cart for user ID: {}", userId);
            shoppingCartService.clearCart(userId);
        } catch (Exception e) {
            logger.error("Failed to complete the purchase for user ID: {}", userId, e);
            throw new RuntimeException("Failed to complete the purchase", e);
        }

        logger.info("Returning updated shopping cart for user ID: {}", userId);
        return ResponseEntity.ok(shoppingCartService.getCartByUserId(userId));
    }

    @PatchMapping("/{shoppingCartId}/update-quantity/{itemId}")
    public ResponseEntity<ShoppingCartItemDto> updateItemQuantity(@PathVariable @NotNull @Min(0) Long itemId, @RequestParam @NotNull @Min(0) int quantity) {
        var item = shoppingCartItemService.getShoppingCartItemById(itemId);
        item.setQuantity(quantity);
        var updatedItem = shoppingCartItemService.updateItemInShoppingCart(item.getShoppingCartId(), item);
        return ResponseEntity.ok(updatedItem);
    }
}
