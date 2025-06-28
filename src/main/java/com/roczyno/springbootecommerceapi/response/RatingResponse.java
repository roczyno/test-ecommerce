package com.roczyno.springbootecommerceapi.response;

public record RatingResponse(
		Long id,
		double rating,
		UserProfileResponse user
) {
}
