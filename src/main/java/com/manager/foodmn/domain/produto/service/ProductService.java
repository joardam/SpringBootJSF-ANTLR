package com.manager.foodmn.domain.produto.service;

import java.util.List;
import java.util.Optional;

import com.manager.foodmn.domain.produto.model.Product;

public interface ProductService {

    public Product save(Product product);

    public List<Product> findAll();

    public Optional<Product> findById(Long id);

    public Product update(Product product);

    public void deleteById(Long id);

    public void deletebyObject(Product product);
}