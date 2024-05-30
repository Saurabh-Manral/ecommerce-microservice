package com.micro.order.externalServices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductService")
public interface ProductService {
	
	@GetMapping("/products/{productId}")
	Product getProduct(@PathVariable String productId);

}
