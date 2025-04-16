package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.OrderItemDto;
import org.bisha.ecommercefinal.exceptions.ResourceAlreadyExistsException;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.OrderItemMapper;
import org.bisha.ecommercefinal.models.OrderItem;
import org.bisha.ecommercefinal.repositories.OrderItemRepository;
import org.bisha.ecommercefinal.repositories.OrderRepository;
import org.bisha.ecommercefinal.repositories.ProductRepository;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.services.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private static final Logger logger = Logger.getLogger(OrderItemServiceImpl.class.getName());

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto){
        logger.info("Creating order item");
        System.out.println("checkpoint");
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        logger.info(orderItem.getPrice() + "");
        logger.info("Creating order item part 2");
        OrderItem savedItem = orderItemRepository.save(orderItem);
        logger.info("order item id is: " + savedItem.getId());
        return orderItemMapper.toDto(savedItem);
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        existingOrderItem.setProduct(productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        existingOrderItem.setUser(userRepository.findById(orderItemDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        existingOrderItem.setOrder(orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found")));
        existingOrderItem.setPrice(orderItemDto.getPrice());

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
        return orderItemMapper.toDto(updatedOrderItem);
    }

    @Override
    public OrderItemDto deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        orderItemRepository.deleteById(id);
        return orderItemMapper.toDto(orderItem);
    }
}