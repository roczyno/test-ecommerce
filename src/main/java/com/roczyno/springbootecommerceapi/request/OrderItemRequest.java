package com.roczyno.springbootecommerceapi.request;

import com.roczyno.springbootecommerceapi.entity.Order;
import com.roczyno.springbootecommerceapi.entity.Product;

import java.time.LocalDateTime;

public record OrderItemRequest(
		Order order,
		Product product,
		String size,
		int quantity,
		Integer price,
		Integer discountedPrice,
		LocalDateTime deliveryDate
) {
}
