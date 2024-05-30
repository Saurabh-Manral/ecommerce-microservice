package com.micro.order.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.micro.order.entities.Order;
import com.micro.order.externalServices.JwtService;
import com.micro.order.services.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JwtService jwtService;
	
	//add new order of user
	@PostMapping
	public ResponseEntity<Order> saveOrder(@RequestBody Order order, HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String userId = jwtService.extractUserId(authHeader.substring(7));
		order.setUserId(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveOrder(order));
	}
	
	//get order with specific id
	@GetMapping("/{orderId}")
	@CircuitBreaker(name = "orderProductCircuitBreaker")
	@Retry(name = "orderProductRetry", fallbackMethod = "orderProductFallback")
	public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(orderId));
	}
	
	//get all orders with associated user
	@GetMapping
	@CircuitBreaker(name = "orderProductCircuitBreaker")
	@Retry(name = "orderProductRetry", fallbackMethod = "orderProductFallback")
	public ResponseEntity<List<Order>> getAllOrder(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		String userId = jwtService.extractUserId(authHeader.substring(7));
		
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findByUser(userId));
	}
	
	//delete order with specific order id
	@DeleteMapping("/{orderId}")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteOrder(@PathVariable String orderId) {
		orderService.deleteOrder(orderId);
	}
	
	//delete all order
	@DeleteMapping
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteAllOrders() {
		orderService.deleteAllOrder();
	}
	
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable String userId) {
//		
//		return ResponseEntity.status(HttpStatus.OK).body(orderService.findByUser(userId));
//		
//	}
	
	//this is the fallback method
	public ResponseEntity<String> orderProductFallback(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server down: " + ex.getMessage());
	}
}
