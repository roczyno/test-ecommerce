package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.Cart;
import com.roczyno.springbootecommerceapi.entity.CartItem;
import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.User;
import com.roczyno.springbootecommerceapi.exception.CartException;
import com.roczyno.springbootecommerceapi.exception.CartItemException;
import com.roczyno.springbootecommerceapi.repository.CartRepository;
import com.roczyno.springbootecommerceapi.request.AddItemRequest;
import com.roczyno.springbootecommerceapi.request.CartItemRequest;
import com.roczyno.springbootecommerceapi.response.CartItemResponse;
import com.roczyno.springbootecommerceapi.response.CartResponse;
import com.roczyno.springbootecommerceapi.service.CartItemService;
import com.roczyno.springbootecommerceapi.service.CartService;
import com.roczyno.springbootecommerceapi.service.ProductService;
import com.roczyno.springbootecommerceapi.util.CartItemMapper;
import com.roczyno.springbootecommerceapi.util.CartMapper;
import com.roczyno.springbootecommerceapi.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final CartMapper cartMapper;
	private final ProductService productService;
	private final ProductMapper productMapper;
	private final CartItemService cartItemService;
	private final CartItemMapper cartItemMapper;

	@Override
	@Transactional
	public void createCart(User user) {
		Cart cart = Cart.builder()
				.user(user)
				.createdAt(LocalDateTime.now())
				.build();
		cartRepository.save(cart);
	}

	@Override
	@Transactional
	public String addCartItem(Authentication connectedUser, AddItemRequest req, Long productId) {
		User user = (User) connectedUser.getPrincipal();
		Cart cart = cartRepository.findByUser(user);
		Product product = productMapper.mapToProduct(productService.findProductById(productId));
		CartItem existingCartItem = cartItemService.isCartItemExist(cart, product, user, req.size());

		if (existingCartItem == null) {
			CartItemRequest cartItemRequest =
					new CartItemRequest(cart, product, req.size(), req.quantity(), product.getPrice(), product.getDiscountPrice());
			CartItemResponse newCartItemResponse = cartItemService.createCartItem(cartItemRequest, user);
			CartItem newCartItem=cartItemMapper.mapToCartItem(newCartItemResponse);
			cart.getCartItems().add(newCartItem);

		} else {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + req.quantity());
		}

		cartRepository.save(cart);
		return "Cart item added/updated successfully";
	}


	@Override
	@Transactional
	public CartResponse findUserCart(Authentication connectedUser) {
		User user = (User) connectedUser.getPrincipal();
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			throw new CartException("Cart does not exists");
		}
		int totalPrice = 0;
		int totalDiscountPrice = 0;
		int totalItems = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountPrice = totalDiscountPrice + cartItem.getDiscountedPrice();
			totalItems = totalItems + cartItem.getQuantity();
		}
		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscount(totalPrice - totalDiscountPrice);
		cart.setTotalItems(totalItems);
		cart.setTotalDiscountedPrice(totalDiscountPrice);
		Cart saveCart=cartRepository.save(cart);
		return cartMapper.mapToCartResponse(saveCart);
	}

	@Override
	@Transactional
	public String removeItemFromCart(Long cartItemId,Authentication connectedUser) {
		User user=(User) connectedUser.getPrincipal();
		CartItem cartItem=cartItemMapper.mapToCartItem(cartItemService.findCartItemById(cartItemId));
		Cart cart=cartMapper.mapToCart(findUserCart(connectedUser));
		if(!Objects.equals(cartItem.getUser().getId(), user.getId())){
			throw new CartItemException("Only the cart Item owner can modify");
		}
		cartItemService.removeCartItem(cartItem.getId());
		cart.getCartItems().remove(cartItem);
		cartRepository.save(cart);

		return "removed successfully";
	}

	@Override
	@Transactional
	public void clearCart(Authentication user) {
		Cart cart=cartMapper.mapToCart(findUserCart(user));
		cartItemService.deleteAllItemsByCartId(cart.getId());
		cart.getCartItems().clear();
	}
}
