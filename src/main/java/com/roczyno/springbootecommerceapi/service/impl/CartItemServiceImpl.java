package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.Cart;
import com.roczyno.springbootecommerceapi.entity.CartItem;
import com.roczyno.springbootecommerceapi.entity.Product;
import com.roczyno.springbootecommerceapi.entity.User;
import com.roczyno.springbootecommerceapi.exception.CartItemException;
import com.roczyno.springbootecommerceapi.repository.CartItemRepository;
import com.roczyno.springbootecommerceapi.request.CartItemRequest;
import com.roczyno.springbootecommerceapi.response.CartItemResponse;
import com.roczyno.springbootecommerceapi.service.CartItemService;
import com.roczyno.springbootecommerceapi.util.CartItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {
	private final CartItemRepository cartItemRepository;
	private final CartItemMapper cartItemMapper;



	@Override
	public CartItemResponse createCartItem(CartItemRequest req, User user) {
		CartItem cartItem = CartItem.builder()
				.quantity(req.quantity())
				.discountedPrice(req.product().getDiscountPrice() * req.quantity())
				.price(req.product().getPrice() * req.quantity())
				.product(req.product())
				.size(req.size())
				.user(user)
				.cart(req.cart())
				.build();
		CartItem savedCartItem = cartItemRepository.save(cartItem);
		return cartItemMapper.mapToCartItemResponse(savedCartItem);
	}

	@Override
	@Transactional
	public CartItemResponse updateCartItem(Authentication connectedUser, Long id, CartItemRequest req) {
		User user=(User) connectedUser.getPrincipal();
		CartItem cartItem=cartItemMapper.mapToCartItem(findCartItemById(id));
		if(!cartItem.getUser().equals(user)){
			throw new CartItemException("Only the cart Item owner can modify");
		}
		cartItem.setQuantity(req.quantity());
		cartItem.setPrice(req.quantity()*req.product().getPrice());
		cartItem.setDiscountedPrice(req.product().getDiscountPrice()* cartItem.getQuantity());
		CartItem updatedCartItem= cartItemRepository.save(cartItem);
		return cartItemMapper.mapToCartItemResponse(updatedCartItem);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, User user, String size) {
		return cartItemRepository.isCartItemExist(product,cart,size,user);
	}

	@Override
	@Transactional
	public void removeCartItem(Long id) {
		CartItem cartItem=cartItemMapper.mapToCartItem(findCartItemById(id));
		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItemResponse findCartItemById(Long cartItemId) {
		CartItem cartItem=cartItemRepository.findById(cartItemId)
				.orElseThrow(()-> new CartItemException("Cart Item not found"));
		return cartItemMapper.mapToCartItemResponse(cartItem);
	}

	@Override
	public void deleteAllItemsByCartId(Long cartId) {
		cartItemRepository.deleteAllByCartId(cartId);
	}
}
