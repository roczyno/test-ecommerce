package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.Rating;
import com.roczyno.springbootecommerceapi.entity.User;
import com.roczyno.springbootecommerceapi.exception.RatingException;
import com.roczyno.springbootecommerceapi.repository.RatingRepository;
import com.roczyno.springbootecommerceapi.request.RatingRequest;
import com.roczyno.springbootecommerceapi.response.RatingResponse;
import com.roczyno.springbootecommerceapi.service.ProductService;
import com.roczyno.springbootecommerceapi.service.RatingService;
import com.roczyno.springbootecommerceapi.util.ProductMapper;
import com.roczyno.springbootecommerceapi.util.RatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
	private final RatingRepository ratingRepository;
	private final ProductService productService;
	private final ProductMapper productMapper;
	private final RatingMapper ratingMapper;

	@Override
	@Transactional
	public RatingResponse createRating(RatingRequest req, Long productId, Authentication connectedUser) {
		User user=(User) connectedUser.getPrincipal();
		Product product=productMapper.mapToProduct(productService.findProductById(productId));
		Optional<Rating> isRatingExist=ratingRepository.findByUserAndProduct(user,product);
		if (isRatingExist.isPresent()){
			throw new RatingException("You can only rate this product once");
		}
		Rating rating=Rating.builder()
				.user(user)
				.product(product)
				.rating(req.rating())
				.createdAt(LocalDateTime.now())
				.build();
		Rating saveRating=ratingRepository.save(rating);
		return ratingMapper.mapToRatingResponse(saveRating);
	}

	@Override
	public List<RatingResponse> getProductRatings(long productId) {
		Product product=productMapper.mapToProduct(productService.findProductById(productId));
		List<Rating> ratings=ratingRepository.findByProduct(product);

		return ratings.stream()
				.map(ratingMapper::mapToRatingResponse)
				.toList();
	}

	@Override
	@Transactional
	public String deleteRating(Long id, Authentication connectedUser) {
		User user=(User) connectedUser.getPrincipal();
		Rating rating=ratingMapper.mapToRating(getRating(id));
		verifyOwnership(user,rating);
		ratingRepository.delete(rating);
		return "Deleted successfully";
	}

	@Override
	@Transactional
	public RatingResponse updateRating(Long id, Authentication connectedUser, RatingRequest req) {
		User user=(User) connectedUser.getPrincipal();
		Rating rating=ratingMapper.mapToRating(getRating(id));
		verifyOwnership(user,rating);
		rating.setRating(req.rating());
		Rating updatedRating=ratingRepository.save(rating);
		return ratingMapper.mapToRatingResponse(updatedRating);
	}

	@Override
	public RatingResponse getRating(Long id) {
		Optional<Rating> rating=ratingRepository.findById(id);
		if(rating.isEmpty()){
			throw new RatingException("rating not found");
		}
		return ratingMapper.mapToRatingResponse(rating.get());
	}

	@Override
	public double getProductAverageRatings(Long productId) {
		Product product=productMapper.mapToProduct(productService.findProductById(productId));
		List<Rating> ratings=ratingRepository.findByProduct(product);
		return ratings.stream()
				.mapToDouble(Rating::getRating)
				.average()
				.orElse(0.0);
	}


	private void verifyOwnership(User user, Rating rating){
		if(!Objects.equals(user.getId(), rating.getUser().getId())){
			throw new RatingException("Only the owner can modify");
		}
	}
}
