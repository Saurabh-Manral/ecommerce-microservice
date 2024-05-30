package com.micro.order.services;

import java.util.List;

import com.micro.order.entities.Order;

public interface OrderService {

	public Order saveOrder(Order order);
	
	public Order getOrder(String orderId);
	
	public List<Order> getAllOrder();
	
	public void deleteOrder(String orderId);
	
	public void deleteAllOrder();
	
	public List<Order> findByUser(String userId);
}
