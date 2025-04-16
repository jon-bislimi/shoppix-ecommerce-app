package org.bisha.ecommercefinal.services.impls;

import jakarta.transaction.Transactional;
import org.bisha.ecommercefinal.dtos.*;
import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.exceptions.WrongPasswordException;
import org.bisha.ecommercefinal.mappers.OrderItemMapper;
import org.bisha.ecommercefinal.mappers.UserMapper;
import org.bisha.ecommercefinal.models.User;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.services.OrderItemService;
import org.bisha.ecommercefinal.services.OrderService;
import org.bisha.ecommercefinal.services.ProductService;
import org.bisha.ecommercefinal.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class
UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, OrderItemMapper orderItemMapper, ProductService productService, OrderService orderService,  OrderItemService orderItemService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderItemMapper = orderItemMapper;
        this.productService = productService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    public UserDto getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        User user = userMapper.toEntity(userDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getUsersByRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Invalid role");
        }
        List<User> users = userRepository.findByRole(role);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with the given role");
        }
        return userMapper.toDtoList(users);
    }

    @Override
    public List<UserDto> getUsersByActivity(boolean active) {
        List<User> users = userRepository.findByActive(active);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with the given activity status");
        }
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        if (userDto == null || userDto.getName() == null || userDto.getEmail() == null || userDto.getUsername() == null) {
            throw new IllegalArgumentException("Invalid user details");
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        //userMapper.updateEntityFromDto(userDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(newPassword); // Ensure to hash the password before saving
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.isActive()) {
            throw new IllegalArgumentException("User is already active");
        }
        user.setActive(true);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isActive()) {
            throw new IllegalArgumentException("User is already inactive");
        }
        user.setActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto resetPassword(String email, String newPassword) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(newPassword);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<OrderItemDto> getBoughtProductsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderItemMapper.toDtoList(user.getBoughtProducts());
    }

    @Override
    @Transactional
    public ProductDto buyProduct(Long userId, Long productId, int quantity) {
        // Validate user
        logger.info("Validating user with ID: {}", userId);
        validateUser(userId);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        logger.info("User validated: {}", user);

        // Validate product
        logger.info("Fetching and validating product ID: {} for quantity: {}", productId, quantity);
        ProductDto productDto = productService.getProductById(productId);
        validateAvailableStock(productDto, quantity);
        logger.info("Product validated: {}", productDto);

        // Calculate total price
        double totalPrice = productDto.getPrice() * quantity;
        logger.info("Total price calculated: {}", totalPrice);

        // Update stock
        logger.info("Updating stock for product ID: {}", productId);
        productDto.setStock(productDto.getStock() - quantity);
        productService.updateProductByIdTemp(productId, productDto);
        logger.info("Stock updated for product ID: {}", productId);

        // Create order
        logger.info("Creating order for user ID: {}", userId);
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setOrderedAt(LocalDateTime.now());
        orderDto.setStatus(OrderStatus.PENDING);

        // Save order to get the ID
        orderDto = orderService.createOrder(orderDto);
        logger.info("Order created with ID: {}", orderDto.getId());

        // Create order item
        logger.info("Creating order item for product ID: {}", productId);
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(productId);
        orderItemDto.setPrice(totalPrice);
        orderItemDto.setUserId(userId);
        orderItemDto.setQuantity(quantity);
        orderItemDto.setOrderId(orderDto.getId());

        // Save order item
        orderItemDto = orderItemService.createOrderItem(orderItemDto);
        logger.info("Order item created with ID: {}", orderItemDto.getId());

        // Add order item to order
        logger.info("Adding order item to order");
        orderDto.getOrderItemIds().add(orderItemDto.getId());
        orderDto.setTotalPrice(totalPrice);
        logger.info("Total price set for order: {}", totalPrice);

        // Update user bought products
        logger.info("Updating user's bought products");
        user.getBoughtProducts().add(orderItemMapper.toEntity(orderItemDto));

        // Save user
        logger.info("Saving updated user information");
        userRepository.save(user);

        logger.info("Updating order with ID: {}", orderDto.getId());
        orderService.updateOrder(orderDto.getId(), orderDto);

        logger.info("Purchase completed successfully for product ID: {}", productId);
        return productDto;
    }

    @Override
    @Transactional
    public List<ProductDto> buyProducts(Long userId, HashMap<Long, Integer> productIdsAndQuantities) {

        // Step 1: Validate user
        logger.info("Validating user with ID: {}", userId);
        validateUser(userId);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        logger.info("User validated: {}", user);

        // Step 2: Initialize the order
        logger.info("Initializing order for user ID: {}", userId);
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setOrderedAt(LocalDateTime.now());
        orderDto.setStatus(OrderStatus.PENDING);
        orderDto = orderService.createOrder(orderDto); // Save to get ID
        logger.info("Order initialized with ID: {}", orderDto.getId());

        // List to keep track of purchased products
        List<ProductDto> purchasedProducts = new ArrayList<>();

        // Step 3: Process each product in the HashMap
        for (Map.Entry<Long, Integer> entry : productIdsAndQuantities.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();
            logger.info("Processing product ID: {} with quantity: {}", productId, quantity);

            // Validate product and stock
            ProductDto productDto = productService.getProductById(productId);
            validateAvailableStock(productDto, quantity);
            logger.info("Product validated: {}", productDto);

            // Calculate total price for this product
            double totalPrice = productDto.getPrice() * quantity;
            logger.info("Total price for product ID: {} is: {}", productId, totalPrice);

            // Update stock
            productDto.setStock(productDto.getStock() - quantity);
            productService.updateProductByIdTemp(productId, productDto);
            logger.info("Stock updated for product ID: {}", productId);

            // Create order item
            OrderItemDto orderItemDto = new OrderItemDto();
            logger.info("product ID: {}", productId);
            orderItemDto.setProductId(productId);
            orderItemDto.setPrice(totalPrice);
            logger.info("User ID: {}", userId);
            orderItemDto.setUserId(userId);
            orderItemDto.setQuantity(quantity);
            logger.info("Order ID: {}", orderDto.getId());
            orderItemDto.setOrderId(orderDto.getId());

            // Save order item
            orderItemDto = orderItemService.createOrderItem(orderItemDto);
            logger.info("Order item created for product ID: {}", productId);

            // Add the created order item to the order
            orderDto.getOrderItemIds().add(orderItemDto.getId());
            orderDto.setTotalPrice(orderDto.getTotalPrice() + orderItemDto.getPrice());
            logger.info("Order item added to order ID: {}", orderDto.getId());

            // Add order item to user's bought products
            user.getBoughtProducts().add(orderItemMapper.toEntity(orderItemDto));
            logger.info("Order item added to user's bought products for user ID: {}", userId);

            // Add product to the list of purchased products
            purchasedProducts.add(productDto);
            logger.info("Product added to purchased products list: {}", productDto);
        }

        // Save user
        userRepository.save(user);
        logger.info("User saved: {}", user);

        orderService.updateOrder(orderDto.getId(), orderDto);
        logger.info("Order updated with total price: {}", orderDto.getTotalPrice());

        // Step 5: Return the list of purchased products
        logger.info("Returning list of purchased products for user ID: {}", userId);
        return purchasedProducts;
    }

    @Override
    @Transactional
    public ProductDto returnProduct(Long userId, Long productId, int quantity) {
        validateUser(userId);
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        List<Long> orderItemIds = orders.stream()
                .flatMap(orderDto -> orderDto.getOrderItemIds().stream())
                .toList();
        boolean isProductBought = false;
        Long orderId = null;
        for (Long id : orderItemIds) {
            OrderItemDto orderItem = orderItemService.getOrderItemById(id);
            if (orderItem.getProductId().equals(productId)) {
                isProductBought = true;
                orderId = orderItem.getOrderId();
                break;
            }
        }
        if (!isProductBought) {
            throw new IllegalArgumentException("Product not bought by user");
        }
        ProductDto productDto = productService.getProductById(productId);
        productDto.setStock(productDto.getStock() + quantity);
        productService.updateProductByIdTemp(productId, productDto);
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getBoughtProducts().remove(orderItemMapper.toEntity(orderItemService.getOrderItemById(productId)));
        userRepository.save(user);

        OrderDto orderDto = orderService.getOrderById(orderId);
        orderDto.setStatus(OrderStatus.RETURNED);
        orderService.updateOrder(orderId, orderDto);


        return productDto;
    }


    @Override
    public void validateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!user.isActive()) {
            throw new IllegalArgumentException("User is not active");
        }
        if (user.getRole() != Role.ROLE_USER) {
            throw new IllegalArgumentException("User is not a customer");
        }
    }


    private static void validateAvailableStock(ProductDto productDto, int quantity) {
        if (productDto.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
    }

//    private static double calculateTotalPrice(HashMap<Integer, Double> map) {
//        double sum = 0;
//        for (int i : map.keySet()) {
//            sum += i * map.get(i);
//        }
//        return sum;
//    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getPassword().equals(userLoginDto.getPassword())) {
            return userRepository.findByUsername(userLoginDto.getUsername())
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        } else {
            throw new WrongPasswordException("Invalid password");
        }
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setName(userRegisterDto.getName());
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        user.setRole(userRegisterDto.getRole());
        user.setBirthDate(userRegisterDto.getBirthDate());
        user.setAddress(userRegisterDto.getAddress());
        user.setProfilePictureURL(userRegisterDto.getProfilePictureURL());
        user.setTelephoneNumber(userRegisterDto.getTelephoneNumber());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return userMapper.toDto(user);
    }
}