package com.micro.product.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException() {
		super();
	}
	
	public ProductNotFoundException(String msg) {
		super(msg);
	}
}
