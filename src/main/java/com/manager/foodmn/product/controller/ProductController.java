package com.manager.foodmn.product.controller;

import com.manager.foodmn.product.model.Product;
import com.manager.foodmn.product.repository.ProductRepository;
import com.manager.foodmn.product.service.ProductService;
import com.manager.foodmn.product.service.ProductServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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