package com.roczyno.springbootecommerceapi.request;

import com.roczyno.springbootecommerceapi.entity.Category;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

public record ProductRequest(
		@NotNull
		String title,
		@NotNull
		String description,
		@NotNull
		int price,
		@NotNull
		int discountPrice,
		@NotNull
		int discountPricePercent,
		@NotNull
		int quantity,
		@NotNull
		String brand,
		@NotNull
		String color,
		@NotNull
		Set<String> sizes,
		@NotNull
		String imageUrl,
		@NotNull
		Category category
) {
}
