package com.roczyno.springbootecommerceapi.response;

import com.roczyno.springbootecommerceapi.entity.Cart;
import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.User;

public record CartItemResponse(
		 Long id,
		 Product product,
		 Cart cart,
		 User user,
		 String size,
		 int quantity,
		 Integer price,
		 Integer discountedPrice
) {
}
