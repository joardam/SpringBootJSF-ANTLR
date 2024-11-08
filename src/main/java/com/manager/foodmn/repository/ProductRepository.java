package com.manager.foodmn.repository;

import com.manager.foodmn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}