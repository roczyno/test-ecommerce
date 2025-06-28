package com.roczyno.springbootecommerceapi.util;

import com.roczyno.springbootecommerceapi.entity.OrderItem;
import com.roczyno.springbootecommerceapi.response.OrderItemResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {
	public OrderItemResponse toOrderItemResponse(OrderItem req) {
		return new OrderItemResponse(
				req.getOrder(),
				req.getProduct(),
				req.getSize(),
				req.getQuantity(),
				req.getPrice(),
				req.getDiscountedPrice(),
				req.getDeliveryDate()
		);
	}

	public OrderItem toOrderItem(OrderItemResponse orderRes) {
		return OrderItem.builder()
				.order(orderRes.order())
				.product(orderRes.product())
				.size(orderRes.size())
				.quantity(orderRes.quantity())
				.price(orderRes.price())
				.discountedPrice(orderRes.discountedPrice())
				.deliveryDate(orderRes.deliveryDate())
				.build();
	}
}
