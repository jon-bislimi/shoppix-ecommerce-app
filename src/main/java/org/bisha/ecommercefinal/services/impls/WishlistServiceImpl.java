package org.bisha.ecommercefinal.services.impls;

import jakarta.transaction.Transactional;
import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.dtos.WishlistDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.ProductMapper;
import org.bisha.ecommercefinal.mappers.WishlistMapper;
import org.bisha.ecommercefinal.models.Wishlist;
import org.bisha.ecommercefinal.repositories.ProductRepository;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.repositories.WishlistRepository;
import org.bisha.ecommercefinal.services.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, WishlistMapper wishlistMapper, UserRepository userRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMapper = wishlistMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public WishlistDto createWishlist(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (wishlistRepository.existsByUser(user)) {
            throw new ResourceAlreadyExistsException("User already has a wishlist");
        }
        var wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    public WishlistDto getWishlistByUserId(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    @Transactional
    public ProductDto addProductToWishlist(Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (wishlist.getProducts().contains(product)) {
            throw new ResourceAlreadyExistsException("Product is already in the wishlist");
        }
        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto removeProductFromWishlist(Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!wishlist.getProducts().contains(product)) {
            throw new ResourceNotFoundException("Product is not in the wishlist");
        }
        wishlist.getProducts().remove(product);
        wishlistRepository.save(wishlist);
        return productMapper.toDto(product);
    }

    @Override
    public WishlistDto clearWishlist(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        if (wishlist.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Wishlist is already empty");
        }
        wishlist.getProducts().clear();
        wishlistRepository.save(wishlist);
        return wishlistMapper.toDto(wishlist);
    }

    @Override
    public List<ProductDto> getAllProductsInWishlist(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        return productMapper.toDtoList(wishlist.getProducts());
    }

    @Override
    public boolean isProductInWishlist(Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return wishlist.getProducts().contains(product);
    }

    @Override
    public WishlistDto duplicateWishlist(Long wishlistId, Long userId) {
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (wishlistRepository.existsByUser(user)) {
            throw new ResourceAlreadyExistsException("User already has a wishlist");
        }
        var newWishlist = new Wishlist();
        newWishlist.setUser(user);
        newWishlist.setProducts(wishlist.getProducts());
        wishlistRepository.save(newWishlist);
        return wishlistMapper.toDto(newWishlist);
    }

    @Override
    public WishlistDto getWishlistById(Long wishlistId) {
        var wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        return wishlistMapper.toDto(wishlist);
    }
}