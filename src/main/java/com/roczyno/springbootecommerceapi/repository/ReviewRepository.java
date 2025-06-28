package com.roczyno.springbootecommerceapi.repository;

import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.Review;
import com.roczyno.springbootecommerceapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
	@Query("select r from Review r where r.product=:product")
	Page<Review> findByProduct(@Param("product") Product product, Pageable pageable);

	Optional<Review> findByUserAndProduct(User user, Product product);
}
