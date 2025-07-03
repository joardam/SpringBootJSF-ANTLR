package com.manager.foodmn.domain.product.repository;

import com.manager.foodmn.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}