package com.micro.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

}
