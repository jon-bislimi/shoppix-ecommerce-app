package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.ShoppingCartItemDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.ShoppingCartItemMapper;
import org.bisha.ecommercefinal.models.ShoppingCart;
import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.bisha.ecommercefinal.repositories.ShoppingCartItemRepository;
import org.bisha.ecommercefinal.repositories.ShoppingCartRepository;
import org.bisha.ecommercefinal.services.ShoppingCartItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartItemServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartItemMapper shoppingCartItemMapper, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemMapper = shoppingCartItemMapper;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public List<ShoppingCartItemDto> getItemsByShoppingCartId(Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCartItemDto getItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem item = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
        return shoppingCartItemMapper.toDto(item);
    }

    @Override
    public List<ShoppingCartItemDto> removeAllItemsByShoppingCartId(Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        List<ShoppingCartItemDto> items = shoppingCart.getItems().stream()
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);
        return items;
    }

    @Override
    public ShoppingCartItemDto removeItemByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem item = shoppingCart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
        shoppingCart.getItems().remove(item);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(item);
    }

    @Override
    public List<ShoppingCartItemDto> getItemsByShoppingCartIdAndQuantityGreaterThan(Long shoppingCartId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .filter(item -> item.getQuantity() > quantity)
                .map(shoppingCartItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCartItemDto addItemToShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        if (shoppingCart.getItems().stream().anyMatch(item -> item.getProduct().getId() == shoppingCartItemDto.getProductId())) {
            throw new ResourceAlreadyExistsException("Product already exists in cart");
        }
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.toEntity(shoppingCartItemDto);
        shoppingCart.getItems().add(shoppingCartItem);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(shoppingCartItem);
    }

    @Override
    public ShoppingCartItemDto updateItemInShoppingCart(Long shoppingCartId, ShoppingCartItemDto shoppingCartItemDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        ShoppingCartItem shoppingCartItem = shoppingCart.getItems().stream()
                .filter(item -> item.getProduct().getId() == shoppingCartItemDto.getProductId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
        shoppingCartItem.setQuantity(shoppingCartItemDto.getQuantity());
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartItemMapper.toDto(shoppingCartItem);
    }

    @Override
    public boolean existsByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found for ID: " + shoppingCartId));
        return shoppingCart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId() == productId);
    }

    @Override
    public ShoppingCartItemDto getShoppingCartItemById(Long shoppingCartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findById(shoppingCartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart item not found for ID: " + shoppingCartItemId));
        return shoppingCartItemMapper.toDto(shoppingCartItem);
    }
}