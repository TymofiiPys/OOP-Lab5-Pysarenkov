package com.restaurant.repository;

import com.restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(Order.StatusOrder statusOrder);
    List<Order> findAllByStatusAndClientId(Order.StatusOrder statusOrder, Long clientId);
}
