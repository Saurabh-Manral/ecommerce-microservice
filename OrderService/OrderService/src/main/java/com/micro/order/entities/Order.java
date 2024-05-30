package com.micro.order.entities;

import com.micro.order.externalServices.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Order {
	
	@Id
	private String orderId;
	private int unit;
	@Column(name = "product_id")
	private String productId;
	@Column(name = "user_id")
	private String userId;
	
	@Transient
	private Product product;
}
