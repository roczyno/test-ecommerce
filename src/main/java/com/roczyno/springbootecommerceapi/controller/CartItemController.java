package com.roczyno.springbootecommerceapi.controller;

import com.roczyno.springbootecommerceapi.request.CartItemRequest;
import com.roczyno.springbootecommerceapi.service.CartItemService;
import com.roczyno.springbootecommerceapi.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart-item")
@RequiredArgsConstructor
public class CartItemController {
	private final CartItemService cartItemService;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getCartItem(@PathVariable Long id){
		return ResponseHandler.successResponse(cartItemService.findCartItemById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCartITem(@PathVariable Long id, Authentication connectedUser,
												 @RequestBody CartItemRequest req){
		return ResponseHandler.successResponse(cartItemService.updateCartItem(connectedUser,id,req),HttpStatus.OK);
	}

}
