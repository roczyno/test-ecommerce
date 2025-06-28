package com.roczyno.springbootecommerceapi.controller;

import com.roczyno.springbootecommerceapi.response.OrderResponse;
import com.roczyno.springbootecommerceapi.service.OrderService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<Object> createOrder(Authentication user){
		return ResponseHandler.successResponse(orderService.createOrder(user), HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOrder(@PathVariable Long id){
		return ResponseHandler.successResponse(orderService.findOrderById(id),HttpStatus.OK);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<Page<OrderResponse>> getUserOrderHistories(@PathVariable Integer userId, Pageable pageable){
		return ResponseEntity.ok(orderService.userOrderHistory(userId,pageable));
	}
	@GetMapping
	public ResponseEntity<Page<OrderResponse>> getAllOrders(Pageable pageable){
		return ResponseEntity.ok(orderService.getAllOrders(pageable));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateOrderStatus(@PathVariable Long id, @RequestParam String status){
		return ResponseHandler.successResponse(orderService.updateOrderStatus(id,status),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Object>deleteOrder(@PathVariable Long id){
		return ResponseHandler.successResponse(orderService.deleteOrder(id),HttpStatus.OK);
	}

}
