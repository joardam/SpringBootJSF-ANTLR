package com.manager.foodmn.product.repository;

import com.manager.foodmn.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}