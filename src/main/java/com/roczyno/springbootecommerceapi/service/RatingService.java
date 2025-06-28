package com.roczyno.springbootecommerceapi.service;

import com.roczyno.springbootecommerceapi.request.RatingRequest;
import com.roczyno.springbootecommerceapi.response.RatingResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RatingService {
	RatingResponse createRating(RatingRequest req,Long productId, Authentication connectedUser);
	List<RatingResponse> getProductRatings(long productId);
	String deleteRating (Long id,Authentication connectedUser);
	RatingResponse updateRating(Long id,Authentication connectedUser,RatingRequest req);
	RatingResponse getRating(Long id);
	double getProductAverageRatings(Long productId);
}
