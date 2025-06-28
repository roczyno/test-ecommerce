package com.roczyno.springbootecommerceapi.controller;

import com.roczyno.springbootecommerceapi.request.AddItemRequest;
import com.roczyno.springbootecommerceapi.service.CartService;
import com.roczyno.springbootecommerceapi.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class CartController {
	private final CartService cartService;

	@PostMapping("/add/product/{productId}")
	public ResponseEntity<Object> addCartItem(@PathVariable Long productId, @RequestBody AddItemRequest req,
											  Authentication connectedUser){
		return ResponseHandler.successResponse(cartService.addCartItem(connectedUser,req,productId), HttpStatus.OK);
	}
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Object> removeCartItem(@PathVariable Long id, Authentication connectedUser){
		return ResponseHandler.successResponse(cartService.removeItemFromCart(id,connectedUser),HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<Object> findUserCart(Authentication connectedUser){
		return ResponseHandler.successResponse(cartService.findUserCart(connectedUser),HttpStatus.OK);
	}


}
