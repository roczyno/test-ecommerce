package com.roczyno.springbootecommerceapi.service;

import com.roczyno.springbootecommerceapi.request.ReviewRequest;
import com.roczyno.springbootecommerceapi.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ReviewService {
	ReviewResponse createReview(ReviewRequest req, Authentication connected, Long productId);
	Page<ReviewResponse> getProductReviews(Long productId, Pageable pageable);
	String deleteReview(Long id,Authentication connectedUser);
	ReviewResponse updateReview(Long id,Authentication connectedUser,ReviewRequest req);
	ReviewResponse getReview(Long id);
}
