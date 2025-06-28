package com.roczyno.springbootecommerceapi.util;

import com.roczyno.springbootecommerceapi.entity.Order;
import com.roczyno.springbootecommerceapi.response.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
	public OrderResponse mapToOrderResponse(Order order) {
		return new OrderResponse(
				order.getId(),
				order.getUser(),
				order.getOrderItems(),
				order.getOrderDate(),
				order.getDeliveryDate(),
				order.getShippingAddress(),
				order.getTotalPrice(),
				order.getTotalDiscountedPrice(),
				order.getDiscount(),
				order.getOrderStatus(),
				order.getTotalItems(),
				order.getCreatedAt()
		);
	}

	public Order mapToOrder(OrderResponse orderRes) {
		return Order.builder()
				.id(orderRes.id())
				.user(orderRes.user())
				.orderItems(orderRes.orderItems())
				.orderDate(orderRes.orderDate())
				.deliveryDate(orderRes.deliveryDate())
				.shippingAddress(orderRes.shippingAddress())
				.totalPrice(orderRes.totalPrice())
				.totalDiscountedPrice(orderRes.totalDiscountedPrice())
				.discount(orderRes.discount())
				.orderStatus(orderRes.orderStatus())
				.totalItems(orderRes.totalItems())
				.createdAt(orderRes.createdAt())
				.build();
	}
}
