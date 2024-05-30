package com.micro.order.exceptions;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException() {
		super();
	}
	
	public OrderNotFoundException(String msg) {
		super(msg);
	}
}
