package com.roczyno.springbootecommerceapi.response;

import java.time.LocalDateTime;

public record ReviewResponse(
		Long id,
		String review,
		UserProfileResponse user,
		LocalDateTime createdAt) {
}
