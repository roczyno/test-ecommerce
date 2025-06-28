package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.Cart;
import com.roczyno.springbootecommerceapi.entity.CartItem;
import com.roczyno.springbootecommerceapi.entity.Order;
import com.roczyno.springbootecommerceapi.entity.OrderItem;
import com.roczyno.springbootecommerceapi.entity.User;
import com.roczyno.springbootecommerceapi.exception.OrderException;
import com.roczyno.springbootecommerceapi.repository.OrderRepository;
import com.roczyno.springbootecommerceapi.request.OrderItemRequest;
import com.roczyno.springbootecommerceapi.response.OrderResponse;
import com.roczyno.springbootecommerceapi.service.CartService;
import com.roczyno.springbootecommerceapi.service.OrderItemService;
import com.roczyno.springbootecommerceapi.service.OrderService;
import com.roczyno.springbootecommerceapi.service.UserService;
import com.roczyno.springbootecommerceapi.util.CartMapper;
import com.roczyno.springbootecommerceapi.util.OrderItemMapper;
import com.roczyno.springbootecommerceapi.util.OrderMapper;
import com.roczyno.springbootecommerceapi.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	private final CartService cartService;
	private final CartMapper cartMapper;
	private final OrderItemService orderItemService;
	private final OrderItemMapper orderItemMapper;
	private final UserService userService;
	private final UserMapper userMapper;


	@Override
	@Transactional
	public OrderResponse createOrder(Authentication connectedUser) {
		User user=(User) connectedUser.getPrincipal();
		Cart cart=cartMapper.mapToCart(cartService.findUserCart(connectedUser));

		List<OrderItem> orderItems= new ArrayList<>();

		for(CartItem item:cart.getCartItems()){
			OrderItem orderItem=OrderItem.builder()
					.price(item.getPrice())
					.product(item.getProduct())
					.quantity(item.getQuantity())
					.size(item.getSize())
					.discountedPrice(item.getDiscountedPrice())
					.build();
			OrderItem createdOrderItem= orderItemMapper.toOrderItem(orderItemService.createOrderItem(new OrderItemRequest(
					orderItem.getOrder(),
					orderItem.getProduct(),
					orderItem.getSize(),
					orderItem.getQuantity(),
					orderItem.getPrice(),
					orderItem.getDiscountedPrice(),
					orderItem.getDeliveryDate()
			)));
			orderItems.add(createdOrderItem);
		}

		Order createdOrder=Order.builder()
				.user(user)
				.orderItems(orderItems)
				.totalPrice(cart.getTotalPrice())
				.totalDiscountedPrice(cart.getTotalDiscount())
				.discount(cart.getTotalDiscount())
				.totalItems(cart.getTotalItems())
				.shippingAddress(user.getAddress())
				.orderDate(LocalDateTime.now())
				.orderStatus("PLACED")
				.deliveryDate(LocalDateTime.now().plusDays(7))
				.createdAt(LocalDateTime.now())
				.build();
		Order savedOrder=orderRepository.save(createdOrder);

		for(OrderItem item: orderItems){
			item.setOrder(savedOrder);
		}
		cartService.clearCart(connectedUser);
		return orderMapper.mapToOrderResponse(savedOrder);
	}

	@Override
	public OrderResponse findOrderById(Long orderId) {
		Order order=orderRepository.findById(orderId)
				.orElseThrow(()-> new OrderException("Order not found"));
		return orderMapper.mapToOrderResponse(order);
	}

	@Override
	public Page<OrderResponse> userOrderHistory(Integer userId, Pageable pageable) {
		User user=userMapper.toUser(userService.getUserById(userId));
		Page<Order> userOrders=orderRepository.findUserOrders(user,pageable);
		return userOrders.map(orderMapper::mapToOrderResponse);

	}


	@Override
	@Transactional
	public OrderResponse updateOrderStatus(Long orderId,String status) {
		Order order=orderMapper.mapToOrder(findOrderById(orderId));
		order.setOrderStatus(status);
		orderRepository.save(order);
		return orderMapper.mapToOrderResponse(order);
	}

	@Override
	public Page<OrderResponse> getAllOrders(Pageable pageable) {
		Page<Order> orders= orderRepository.findAll(pageable);
		return orders.map(orderMapper::mapToOrderResponse);

	}

	@Override
	@Transactional
	public String deleteOrder(Long orderId) {
		Order order=orderMapper.mapToOrder(findOrderById(orderId));
		orderRepository.delete(order);
		return "Order deleted successfully";
	}
}
