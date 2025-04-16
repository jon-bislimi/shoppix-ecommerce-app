package org.bisha.ecommercefinal.services.impls;

import jakarta.transaction.Transactional;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.ShoppingCartDto;
import org.bisha.ecommercefinal.dtos.ShoppingCartItemDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.ProductDtoToShoppingCartItemMapper;
import org.bisha.ecommercefinal.mappers.ShoppingCartItemMapper;
import org.bisha.ecommercefinal.mappers.ShoppingCartMapper;
import org.bisha.ecommercefinal.models.ShoppingCart;
import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.bisha.ecommercefinal.repositories.ShoppingCartRepository;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.services.ShoppingCartItemService;
import org.bisha.ecommercefinal.services.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ProductDtoToShoppingCartItemMapper productToShoppingCartItemMapper;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final UserRepository userRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper, ProductDtoToShoppingCartItemMapper productToShoppingCartItemMapper, ShoppingCartItemService shoppingCartItemService, ShoppingCartItemMapper shoppingCartItemMapper, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.productToShoppingCartItemMapper = productToShoppingCartItemMapper;
        this.shoppingCartItemService = shoppingCartItemService;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ShoppingCartDto createCart(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        shoppingCart.setTotalPrice(0);
        shoppingCart.setCreatedAt(LocalDateTime.now());
        shoppingCart.setUpdatedAt(LocalDateTime.now());

        ShoppingCart createdCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(createdCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto addProductToCart(Long userId, ProductDto product) {
        ShoppingCart shoppingCart;
        if (!shoppingCartRepository.existsByUserId(userId)) {
            shoppingCart = shoppingCartMapper.toEntity(createCart(userId));
        } else {
            shoppingCart = shoppingCartRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));
        }

        if (shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), product.getId())) {
            throw new ResourceAlreadyExistsException("Product already exists in cart");
        }

        ShoppingCartItem shoppingCartItem = productToShoppingCartItemMapper.mapToShoppingCartItem(product);
        ShoppingCartItemDto shoppingCartItemDto = shoppingCartItemMapper.toDto(shoppingCartItem);
        shoppingCartItemService.addItemToShoppingCart(shoppingCart.getId(), shoppingCartItemDto);

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + product.getPrice());
        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto removeProductFromCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        ShoppingCartItemDto removedItem = shoppingCartItemService.removeItemByShoppingCartIdAndProductId(shoppingCart.getId(), productId);

        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - removedItem.getPrice());
        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public ShoppingCartDto getCartByUserId(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto clearCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        shoppingCartItemService.removeAllItemsByShoppingCartId(shoppingCart.getId());
        shoppingCart.setTotalPrice(0);

        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return shoppingCartMapper.toDto(updatedCart);
    }

    @Override
    public double getTotalPrice(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getTotalPrice();
    }

    @Override
    public double updateCartTotalPrice(Long userId, double newPrice) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        double totalPrice = getTotalPrice(userId);

        ShoppingCart updatedCart = shoppingCartRepository.save(shoppingCart);

        return updatedCart.getTotalPrice();
    }

    @Override
    public int getProductCount(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCart.getItems().size();
    }

    @Override
    public boolean isProductInCart(Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for user ID: " + userId));

        return shoppingCartItemService.existsByShoppingCartIdAndProductId(shoppingCart.getId(), productId);
    }
}