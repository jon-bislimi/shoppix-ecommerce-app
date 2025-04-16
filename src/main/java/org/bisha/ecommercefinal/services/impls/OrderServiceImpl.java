package org.bisha.ecommercefinal.services.impls;

import org.bisha.ecommercefinal.dtos.OrderDto;
import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.enums.Role;
import org.bisha.ecommercefinal.exceptions.ResourceNotFoundException;
import org.bisha.ecommercefinal.mappers.OrderMapper;
import org.bisha.ecommercefinal.models.Order;
import org.bisha.ecommercefinal.models.OrderItem;
import org.bisha.ecommercefinal.repositories.OrderItemRepository;
import org.bisha.ecommercefinal.repositories.OrderRepository;
import org.bisha.ecommercefinal.repositories.UserRepository;
import org.bisha.ecommercefinal.services.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order.setUser(userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        logger.info("Order id is: " + order.getId());
        order.setStatus(orderDto.getStatus());
        order.setTelephoneNumber(orderDto.getTelephoneNumber());
        order.setAddress(orderDto.getAddress());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setCancelledAt(orderDto.getCancelledAt());
        logger.info("test a po mrrin qitu");
        List<OrderItem> orderItems = new ArrayList<>();
        for (Long orderItemId : orderDto.getOrderItemIds()) {
            logger.info("Order item id is: " + orderItemId);
            OrderItem orderItem = orderItemRepository.findById(orderItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
            orderItems.add(orderItem);
        }
        logger.info("Order items size is: " + orderItems.size());
        order.setOrderItems(orderItems);

        logger.info("Almost there");
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    public OrderDto cancelOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        Order cancelledOrder = orderRepository.save(order);
        return orderMapper.toDto(cancelledOrder);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRole() == Role.ROLE_ADMIN){
            return getAllOrders();
        }
        List<Order> orders = orderRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }


    @Override
    public List<OrderDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByOrderedAtAfter(startDate)
                .stream()
                .filter(order -> order.getOrderedAt().isBefore(endDate))
                .collect(Collectors.toList());
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByTotalPriceRange(double minPrice, double maxPrice) {
        List<Order> orders = orderRepository.findByTotalPriceBetween(minPrice, maxPrice);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderAddressAndPhone(Long orderId, String address, String phone) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setAddress(address);
        order.setTelephoneNumber(phone);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDto(updatedOrder);
    }
}