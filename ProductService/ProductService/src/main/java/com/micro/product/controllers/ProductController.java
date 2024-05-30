package com.micro.product.controllers;

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

import com.micro.product.entities.Product;
import com.micro.product.services.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable String productId) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(productId));
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProduct() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct());
	}
	
	@DeleteMapping("/{productId}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteProduct(@PathVariable String productId) {
		productService.deleteProduct(productId);
	}
	
	@DeleteMapping
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteAllProduct() {
		productService.deleteAllProduct();
	}
}
