package com.roczyno.springbootecommerceapi.request;

import jakarta.validation.constraints.NotNull;

public record AddItemRequest(
		@NotNull
		 String size,
		 @NotNull
		 int quantity
) {
}
