package com.roczyno.springbootecommerceapi.service;



import com.roczyno.springbootecommerceapi.request.ProductRequest;
import com.roczyno.springbootecommerceapi.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
	ProductResponse createProduct(ProductRequest req);
	String deleteProduct(Long productId);
	ProductResponse updateProduct(Long productId, ProductRequest req);
	ProductResponse findProductById(Long productId);
	Page<ProductResponse> findProductByCategory(String category, Pageable pageable);
	Page<ProductResponse> getAllProducts(String category, List<String> color, List<String> sizes, Integer minPrice,
										 Integer maxPrice, Integer minDiscount, String stock, String sort, Pageable pageable);

	List<ProductResponse> searchForProducts(String keyword);
}
