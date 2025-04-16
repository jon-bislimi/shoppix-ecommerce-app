package org.bisha.ecommercefinal.mappers;

import org.bisha.ecommercefinal.dtos.ProductDto;
import org.bisha.ecommercefinal.models.ShoppingCartItem;

import java.util.List;


public interface ProductDtoToShoppingCartItemMapper {
    ProductDto ToProduct(ShoppingCartItem shoppingCartItem);
    ShoppingCartItem mapToShoppingCartItem(ProductDto product);

    List<ShoppingCartItem> mapToShoppingCartItems(List<ProductDto> products);
    List<ProductDto> mapToProducts(List<ShoppingCartItem> shoppingCartItems);
}
