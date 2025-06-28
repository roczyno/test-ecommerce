package com.roczyno.springbootecommerceapi.util;

import com.roczyno.springbootecommerceapi.entity.Review;
import com.roczyno.springbootecommerceapi.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewMapper {
	private final UserMapper userMapper;
	public ReviewResponse mapToReviewResponse(Review review) {
		return new ReviewResponse(
				review.getId(),
				review.getReview(),
				userMapper.toUserResponse(review.getUser()),
				review.getCreatedAt()
		);
	}

	public Review mapToReview(ReviewResponse reviewResponse) {
		return Review.builder()
				.id(reviewResponse.id())
				.review(reviewResponse.review())
				.createdAt(reviewResponse.createdAt())
				.user(userMapper.toUser(reviewResponse.user()))
				.build();
	}
}
