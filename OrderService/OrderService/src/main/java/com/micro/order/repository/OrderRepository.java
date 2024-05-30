package com.micro.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.micro.order.entities.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	Optional<List<Order>> findByUserId(String userId);

}
