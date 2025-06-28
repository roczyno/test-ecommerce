package com.roczyno.springbootecommerceapi.controller;

import com.roczyno.springbootecommerceapi.request.RatingRequest;
import com.roczyno.springbootecommerceapi.service.RatingService;
import com.roczyno.springbootecommerceapi.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("rating")
public class RatingController {
	private final RatingService ratingService;

	@PostMapping("/product/{productId}")
	public ResponseEntity<Object> createRating(@PathVariable Long productId, Authentication user, @RequestBody RatingRequest req){
		return ResponseHandler.successResponse(ratingService.createRating(req,productId,user), HttpStatus.OK);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Object>getProductRatings(@PathVariable Long productId){
		return ResponseHandler.successResponse(ratingService.getProductRatings(productId),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteRating(@PathVariable Long id,Authentication user){
		return ResponseHandler.successResponse(ratingService.deleteRating(id,user),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getRating(@PathVariable Long id){
		return ResponseHandler.successResponse(ratingService.getRating(id),HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateRating(@PathVariable Long id,Authentication user,@RequestBody RatingRequest req){
		return ResponseHandler.successResponse(ratingService.updateRating(id,user,req),HttpStatus.OK);
	}
	@GetMapping("/product/{id}/average-ratings")
	public ResponseEntity<Object> productAverageRating(@PathVariable Long id){
		return ResponseHandler.successResponse(ratingService.getProductAverageRatings(id),HttpStatus.OK);
	}

}
