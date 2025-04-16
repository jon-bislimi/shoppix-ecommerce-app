package org.bisha.ecommercefinal.repositories;

import org.bisha.ecommercefinal.enums.OrderStatus;
import org.bisha.ecommercefinal.models.Order;
import org.bisha.ecommercefinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByOrderedAtAfter(LocalDateTime date);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByTotalPriceGreaterThan(double amount);

    List<Order> findByTotalPriceLessThan(double amount);

    List<Order> findByTotalPriceBetween(double min, double max);
}