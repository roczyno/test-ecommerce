package com.roczyno.springbootecommerceapi.service;

import com.roczyno.springbootecommerceapi.request.OrderItemRequest;
import com.roczyno.springbootecommerceapi.response.OrderItemResponse;

public interface OrderItemService {
	OrderItemResponse createOrderItem(OrderItemRequest orderItem);
}
