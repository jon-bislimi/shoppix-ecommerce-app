package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.models.ShoppingCartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoToShoppingCartItemMapperImpl implements ProductDtoToShoppingCartItemMapper {

    private final ProductMapper productMapper;

    public ProductDtoToShoppingCartItemMapperImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }
    @Override
    public ProductDto ToProduct(ShoppingCartItem shoppingCartItem) {
        if (shoppingCartItem == null) {
            return null;
        }
         return productMapper.toDto(shoppingCartItem.getProduct());

    }

    @Override
    public ShoppingCartItem mapToShoppingCartItem(ProductDto product) {
        if (product == null) {
            return null;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setProduct(productMapper.toEntity(product));
        shoppingCartItem.setQuantity(1); // Default quantity, adjust as necessary
        shoppingCartItem.setPrice(product.getPrice());
        return shoppingCartItem;
    }

    @Override
    public List<ShoppingCartItem> mapToShoppingCartItems(List<ProductDto> products) {
        if (products == null) {
            return List.of();
        }
        return products.stream()
                .map(this::mapToShoppingCartItem)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> mapToProducts(List<ShoppingCartItem> shoppingCartItems) {
        if (shoppingCartItems == null) {
            return List.of();
        }
        return shoppingCartItems.stream()
                .map(this::ToProduct)
                .collect(Collectors.toList());
    }
}