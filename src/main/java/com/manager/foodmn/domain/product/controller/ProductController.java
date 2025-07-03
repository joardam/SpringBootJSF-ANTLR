package com.manager.foodmn.domain.product.controller;

import com.manager.foodmn.domain.product.model.Product;
import com.manager.foodmn.domain.product.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.util.List;

@Named
@Data
@ViewScoped
public class ProductController {

    @Inject
    private ProductService productService;

    private List<Product>  products;
    private Product product = new Product();

    private boolean editMode = false;

    @PostConstruct
    public void init() {
        this.products = productService.findAll();
    }

    public void save()
    {
        productService.save(this.product);
        if(!editMode){
            products.add(this.product);
        }
        this.product = new Product();
        this.editMode = false;
    }

    public void delete(Product product){
        productService.deletebyObject(product);
        products.remove(product);
    }

    public void edit(Product product){
        this.editMode = true;
        this.product = product;
    }

    public void cancel(){
        this.editMode = false;
        this.product = new Product();
    }


}