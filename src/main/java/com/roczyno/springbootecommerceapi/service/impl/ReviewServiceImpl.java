package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.Review;
import com.roczyno.springbootecommerceapi.entity.User;
import com.roczyno.springbootecommerceapi.exception.ReviewException;
import com.roczyno.springbootecommerceapi.repository.ReviewRepository;
import com.roczyno.springbootecommerceapi.request.ReviewRequest;
import com.roczyno.springbootecommerceapi.response.ReviewResponse;
import com.roczyno.springbootecommerceapi.service.ProductService;
import com.roczyno.springbootecommerceapi.service.ReviewService;
import com.roczyno.springbootecommerceapi.util.ProductMapper;
import com.roczyno.springbootecommerceapi.util.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;
	private final ProductService productService;
	private final ProductMapper productMapper;

	@Override
	public ReviewResponse createReview(ReviewRequest req, Authentication connectedUser,Long productId) {
		User user=(User) connectedUser.getPrincipal();
		Product product=productMapper.mapToProduct(productService.findProductById(productId));
		Optional<Review> isReviewExist=reviewRepository.findByUserAndProduct(user,product);
		if(isReviewExist.isPresent()){
			throw new ReviewException("You have already reviewed this product.");
		}
		Review review=Review.builder()
				.user(user)
				.review(req.review())
				.product(product)
				.createdAt(LocalDateTime.now())
				.build();
		Review saveReview=reviewRepository.save(review);
		return reviewMapper.mapToReviewResponse(saveReview);
	}

	@Override
	public Page<ReviewResponse> getProductReviews(Long productId, Pageable pageable) {
		Product product=productMapper.mapToProduct(productService.findProductById(productId));
		Page<Review> reviews=reviewRepository.findByProduct(product,pageable);
		return reviews.map(reviewMapper::mapToReviewResponse);
	}

	@Override
	public String deleteReview(Long id, Authentication connectedUser) {
		User user=(User) connectedUser.getPrincipal();
		Review review=reviewMapper.mapToReview(getReview(id));
		verifyOwnership(user,review);
		reviewRepository.delete(review);
		return "Deleted successfully";
	}

	@Override
	public ReviewResponse updateReview(Long id, Authentication connectedUser, ReviewRequest req) {
		User user=(User) connectedUser.getPrincipal();
		Review review=reviewMapper.mapToReview(getReview(id));
	    verifyOwnership(user,review);
		review.setReview(req.review());
		Review updatedReview=reviewRepository.save(review);
		return reviewMapper.mapToReviewResponse(updatedReview);
	}

	@Override
	public ReviewResponse getReview(Long id) {
		Review review=reviewRepository.findById(id)
				.orElseThrow(()-> new ReviewException("review not found"));
		return reviewMapper.mapToReviewResponse(review);
	}
	private void verifyOwnership(User user, Review review) {
		if (!Objects.equals(user.getId(), review.getUser().getId())) {
			throw new ReviewException("Only the owner can modify/delete the review.");
		}
	}

}
