package org.bisha.ecommercefinal.configs;

import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.models.*;
import org.bisha.ecommercefinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    public void init() {
        runCategory();
        runSubcategory();
        runUser();
        runProduct();
//        runOrder();
//        runShoppingCart();
//        runShoppingCartItem();
//        runWishlist();
//        runOrderItem();
//        runReview();
    }

    private void runCategory() {
        if (categoryRepository.count() > 0) return;
        else {
            categoryRepository.save(new Category(null, "No category", "No description for this category", null));
        }
    }
//    private void runOrderItem() {
//        if (orderItemRepository.count() > 0) return;
//        else {
//            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
//            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
//            Order order = orderRepository.findById(1L).orElseThrow(() -> new RuntimeException("Order not found"));
//            orderItemRepository.save(new OrderItem(null, product, user, order, 10.0));
//        }
//    }
//    private void runOrder() {
//        if (orderRepository.count() > 0) return;
//        else {
//            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
//            Order order = new Order(
//                    0L,
//                    user,
//                    OrderStatus.PENDING,
//                    100.0,
//                    "123 Main St",
//                    "+1234567890",
//                    LocalDateTime.now(),
//                    null,
//                    List.of(),
//                    null
//            );
//            orderRepository.save(order);
//        }
//    }
    private void runProduct() {
            if (productRepository.count() > 0) return;
            else {
                Category category = categoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Category not found"));
                Subcategory subcategory = subcategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Subcategory not found"));

                Product laptop = new Product(
                        0L,
                        "Laptop",
                        "High performance laptop with 16GB RAM and 512GB SSD.",
                        1200.0,
                        List.of("products/dell-img.jpg"),
                        List.of(),
                        50,
                        category,
                        "Dell",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                Product smartphone = new Product(
                        0L,
                        "Smartphone",
                        "Latest model smartphone with 8GB RAM and 128GB storage.",
                        800.0,
                        List.of("/products/samsung-img.jpg"),
                        List.of(),
                        200,
                        category,
                        "Samsung",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                Product tablet = new Product(
                        0L,
                        "Tablet",
                        "10-inch tablet with 4GB RAM and 64GB storage.",
                        300.0,
                        List.of("products/tablet-img.jpg"),
                        List.of(),
                        100,
                        category,
                        "Apple",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                Product headphones = new Product(
                        0L,
                        "Headphones",
                        "Noise-cancelling over-ear headphones.",
                        150.0,
                        List.of("products/sony-img.jpg"),
                        List.of(),
                        75,
                        category,
                        "Sony",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                Product smartwatch = new Product(
                        0L,
                        "Smartwatch",
                        "Smartwatch with heart rate monitor and GPS.",
                        250.0,
                        List.of("products/garmin-img.jpg"),
                        List.of(),
                        120,
                        category,
                        "Garmin",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                Product camera = new Product(
                        0L,
                        "Camera",
                        "Digital camera with 24MP sensor and 4K video recording.",
                        900.0,
                        List.of("/products/pexels-madebymath-90946.jpg"),
                        List.of(),
                        60,
                        category,
                        "Canon",
                        0.0,
                        subcategory,
                        true,
                        LocalDate.now()
                );

                productRepository.save(laptop);
                productRepository.save(smartphone);
                productRepository.save(tablet);
                productRepository.save(headphones);
                productRepository.save(smartwatch);
                productRepository.save(camera);

        }
    }

//    private void runReview() {
//        if (reviewRepository.count() > 0) return;
//        else {
//            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
//            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
//            Review review = new Review(
//                    0L,
//                    "Great product!",
//                    5,
//                    product,
//                    user,
//                    LocalDateTime.now()
//            );
//            reviewRepository.save(review);
//        }
//    }

    private void runSubcategory() {
        if (subcategoryRepository.count() > 0) return;
        else {
            Category parentCategory = categoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Parent category not found"));
            subcategoryRepository.save(new Subcategory(null, "No subcategory", "No description for this subcategory", parentCategory));
        }
    }

    private void runUser() {
        if (userRepository.count() > 0) return;
        else {
            User user = new User(
                    0L,
                    "Admin one",
                    "AdminOne",
                    "AdminOne@example.com",
                    "AdminOnePassword123",
                    Role.ROLE_ADMIN,
                    LocalDate.of(1990, 1, 1),
                    "123 Main St",
                    "https://example.com/profile.jpg",
                    LocalDateTime.now(),
                    null,
                    "1234567890",
                    true
            );
            User user2 = new User(
                    0L,
                    "Admin two",
                    "AdminTwo",
                    "AdminTwo@example.com",
                    "AdminTwoPassword123",
                    Role.ROLE_ADMIN,
                    LocalDate.of(1990, 1, 1),
                    "123 Main St",
                    "https://example.com/profile.jpg",
                    LocalDateTime.now(),
                    null,
                    "1234567990",
                    true
            );
            User user3 = new User(
                    0L,
                    "User one",
                    "UserOne",
                    "UserOne@example.com",
                    "UserOnePassword123",
                    Role.ROLE_USER,
                    LocalDate.of(1990, 1, 1),
                    "123 Main St",
                    "https://example.com/profile.jpg",
                    LocalDateTime.now(),
                    null,
                    "1234567990",
                    true
            );
            userRepository.save(user);
            userRepository.save(user2);
            userRepository.save(user3);
        }
    }

//    private void runShoppingCart() {
//        if (shoppingCartRepository.count() > 0) return;
//        else {
//            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
//            ShoppingCart shoppingCart = new ShoppingCart(
//                    null,
//                    user,
//                    List.of(),
//                    0.0,
//                    LocalDateTime.now(),
//                    LocalDateTime.now()
//            );
//            shoppingCartRepository.save(shoppingCart);
//        }
//    }

//    private void runShoppingCartItem() {
//        if (shoppingCartItemRepository.count() > 0) return;
//        else {
//            ShoppingCart shoppingCart = shoppingCartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Shopping cart not found"));
//            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
//            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(
//                    null,
//                    shoppingCart,
//                    product,
//                    1,
//                    10.0
//            );
//            shoppingCartItemRepository.save(shoppingCartItem);
//        }
//    }

//    private void runWishlist() {
//        if (wishlistRepository.count() > 0) return;
//        else {
//            User user = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
//            Product product = productRepository.findById(1L).orElseThrow(() -> new RuntimeException("Product not found"));
//            Wishlist wishlist = new Wishlist(
//                    0L,
//                    user,
//                    List.of(product),
//                    LocalDate.now()
//            );
//            wishlistRepository.save(wishlist);
//        }
//    }
}
