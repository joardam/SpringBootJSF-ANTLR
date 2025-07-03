package com.manager.foodmn.domain.product.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Double price;

    private String unity;

    public Product(String name , Double price , String unity){
        this.name = name;
        this.price = price;
        this.unity = unity;
    }

    public Product() {

    }
}