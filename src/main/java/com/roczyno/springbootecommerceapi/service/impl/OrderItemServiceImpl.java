package com.roczyno.springbootecommerceapi.service.impl;

import com.roczyno.springbootecommerceapi.entity.OrderItem;
import com.roczyno.springbootecommerceapi.repository.OrderItemRepository;
import com.roczyno.springbootecommerceapi.request.OrderItemRequest;
import com.roczyno.springbootecommerceapi.response.OrderItemResponse;
import com.roczyno.springbootecommerceapi.service.OrderItemService;
import com.roczyno.springbootecommerceapi.util.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
	private final OrderItemRepository orderItemRepository;
	private final OrderItemMapper mapper;

	@Override
	public OrderItemResponse createOrderItem(OrderItemRequest request) {
		OrderItem orderItem=OrderItem.builder()
				.order(request.order())
				.product(request.product())
				.size(request.size())
				.quantity(request.quantity())
				.price(request.price())
				.discountedPrice(request.discountedPrice())
				.deliveryDate(request.deliveryDate())
				.build();
		return mapper.toOrderItemResponse(orderItemRepository.save(orderItem));
	}
}
