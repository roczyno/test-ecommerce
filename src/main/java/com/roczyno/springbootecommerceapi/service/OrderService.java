package com.roczyno.springbootecommerceapi.service;

import com.roczyno.springbootecommerceapi.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
	OrderResponse createOrder (Authentication user);
	OrderResponse findOrderById (Long orderId);
	Page<OrderResponse> userOrderHistory(Integer userId, Pageable pageable);
    OrderResponse updateOrderStatus(Long orderId,String status);
	Page<OrderResponse> getAllOrders(Pageable pageable);
	String deleteOrder(Long orderId);
}
