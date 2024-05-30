package com.micro.order.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.order.entities.Order;
import com.micro.order.exceptions.OrderNotFoundException;
import com.micro.order.externalServices.ProductService;
import com.micro.order.repository.OrderRepository;
import com.micro.order.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private ProductService productService;

	//saving new order
	@Override
	public Order saveOrder(Order order) {
		order.setOrderId(UUID.randomUUID().toString());
		return orderRepo.save(order);
	}

	//get specific order with order id
	@Override
	public Order getOrder(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		order.setProduct(productService.getProduct(order.getProductId()));
		
		return order;
	}

	//get all order of user
	@Override
	public List<Order> getAllOrder() {
		List<Order> orders = orderRepo.findAll();
		for(Order o : orders) {
			o.setProduct(productService.getProduct(o.getProductId()));
		}
		
		return orders;
	}

	//delete specific order with order id
	@Override
	public void deleteOrder(String orderId) {
		Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
		orderRepo.delete(order);
	}

	//delete all order
	@Override
	public void deleteAllOrder() {
		orderRepo.deleteAll();
	}

	//find order by user id
	@Override
	public List<Order> findByUser(String userId) {
		
		List<Order> userOrder = orderRepo.findByUserId(userId).orElseThrow(() -> new OrderNotFoundException());
		
		System.out.println("Total orders are: " + userOrder);
		
		for(Order o : userOrder) {
			o.setProduct(productService.getProduct(o.getProductId()));
		}
		System.out.println("Completed");
		
		return userOrder;
		
	}

}
