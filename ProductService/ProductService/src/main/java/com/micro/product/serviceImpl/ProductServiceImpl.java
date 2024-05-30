package com.micro.product.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.product.entities.Product;
import com.micro.product.exceptions.ProductNotFoundException;
import com.micro.product.repository.ProductRepository;
import com.micro.product.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public Product saveProduct(Product product) {
		product.setProductId(UUID.randomUUID().toString());
		return productRepo.save(product);
	}

	@Override
	public Product getProduct(String productId) {
		return productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
	}

	@Override
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	@Override
	public void deleteProduct(String productId) {
		productRepo.delete(productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
	}

	@Override
	public void deleteAllProduct() {
		productRepo.deleteAll();
		
	}

}
