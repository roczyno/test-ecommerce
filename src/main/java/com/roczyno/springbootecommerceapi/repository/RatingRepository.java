package com.roczyno.springbootecommerceapi.repository;

import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.Rating;
import com.roczyno.springbootecommerceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {
	Optional<Rating> findByUserAndProduct(User user, Product product);
	List<Rating> findByProduct(Product product);
}
