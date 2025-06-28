package com.roczyno.springbootecommerceapi.repository;

import com.roczyno.springbootecommerceapi.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
