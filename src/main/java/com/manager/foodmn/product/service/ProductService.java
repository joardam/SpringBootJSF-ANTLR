package com.manager.foodmn.product.service;

import com.manager.foodmn.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product save(Product product);

    public List<Product> findAll();

    public Optional<Product> findById(Long id);

    public Product update(Product product);

    public void deleteById(Long id);

    public void deletebyObject(Product product);
}