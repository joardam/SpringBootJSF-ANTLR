package com.manager.foodmn.domain.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.foodmn.domain.produto.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}