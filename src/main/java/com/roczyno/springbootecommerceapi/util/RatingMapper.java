package com.roczyno.springbootecommerceapi.util;

import com.roczyno.springbootecommerceapi.entity.Rating;
import com.roczyno.springbootecommerceapi.response.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingMapper {
	private final UserMapper userMapper;
	public RatingResponse mapToRatingResponse(Rating rating) {
		return new RatingResponse(
				rating.getId(),
				rating.getRating(),
				userMapper.toUserResponse(rating.getUser())
		);
	}

	public Rating mapToRating(RatingResponse ratingRes) {
		return Rating.builder()
				.id(ratingRes.id())
				.rating(ratingRes.rating())
				.user(userMapper.toUser(ratingRes.user()))
				.build();
	}
}
