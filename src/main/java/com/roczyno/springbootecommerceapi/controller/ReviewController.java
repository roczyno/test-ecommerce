package com.roczyno.springbootecommerceapi.controller;

import com.roczyno.springbootecommerceapi.request.ReviewRequest;
import com.roczyno.springbootecommerceapi.response.ReviewResponse;
import com.roczyno.springbootecommerceapi.service.ReviewService;
import com.roczyno.springbootecommerceapi.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping("/product/{productId}")
	public ResponseEntity<Object> createReview(@PathVariable Long productId,@RequestBody ReviewRequest req, Authentication user){
		return ResponseHandler.successResponse(reviewService.createReview(req,user,productId), HttpStatus.OK);
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<Page<ReviewResponse>> getProductReviews(@PathVariable Long productId, Pageable pageable){
		return ResponseEntity.ok(reviewService.getProductReviews(productId,pageable));
	}
	@GetMapping("/{reviewId}")
	public ResponseEntity<Object> getReview(@PathVariable Long reviewId){
		return ResponseHandler.successResponse(reviewService.getReview(reviewId),HttpStatus.OK);
	}
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Object> deleteReview(@PathVariable Long reviewId,Authentication user){
		return ResponseHandler.successResponse(reviewService.deleteReview(reviewId,user),HttpStatus.OK);
	}
	@PutMapping("/{reviewId}")
	public ResponseEntity<Object> updateReview(@PathVariable Long reviewId,Authentication user,@RequestBody ReviewRequest req){
		return ResponseHandler.successResponse(reviewService.updateReview(reviewId,user,req),HttpStatus.OK);
	}

}
