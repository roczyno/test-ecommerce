package com.roczyno.springbootecommerceapi.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roczyno.springbootecommerceapi.entity.CartItem;
import com.roczyno.springbootecommerceapi.entity.User;

import java.util.List;

public record CartResponse(
		Long id,
		List<CartItem> cartItems,
		double totalPrice,
		int totalItems,
		int totalDiscountedPrice,
		int totalDiscount,
		@JsonIgnore
		User user
) {
}
