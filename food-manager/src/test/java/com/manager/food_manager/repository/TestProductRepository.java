package com.manager.food_manager.repository;

import com.manager.food_manager.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TestProductRepository {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testCreateProduct(){
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);
        Product saveProduct = productRepository.save(product);

        Assert.assertNotNull(saveProduct.getId());
    }



}
