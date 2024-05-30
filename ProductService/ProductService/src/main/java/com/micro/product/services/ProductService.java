package com.micro.product.services;

import java.util.List;

import com.micro.product.entities.Product;

public interface ProductService {

	public Product saveProduct(Product product);
	public Product getProduct(String productId);
	public List<Product> getAllProduct();
	public void deleteProduct(String productId);
	public void deleteAllProduct();
}
