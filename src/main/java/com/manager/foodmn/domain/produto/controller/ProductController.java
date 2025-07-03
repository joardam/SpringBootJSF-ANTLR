package com.manager.foodmn.domain.produto.controller;

import com.manager.foodmn.domain.produto.model.Product;
import com.manager.foodmn.domain.produto.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Named
@Data
@ViewScoped
// Adicionar Serializable é uma boa prática para ViewScoped beans
public class ProductController implements Serializable {

    @Inject
    private ProductService productService;

    private List<Product> products;
    private Product product = new Product();

    // MUDANÇA: Objeto para guardar o produto selecionado para exclusão
    private Product selectedProduct;

    private boolean editMode = false;

    @PostConstruct
    public void init() {
        this.products = productService.findAll();
    }

    public void save() {
        String message;
        if (editMode) {
            productService.save(this.product);
            // Atualiza o objeto na lista para refletir a mudança na tabela
            int index = products.indexOf(this.product);
            if (index != -1) {
                products.set(index, this.product);
            }
            message = "Produto atualizado com sucesso!";
        } else {
            productService.save(this.product);
            products.add(this.product);
            message = "Produto salvo com sucesso!";
        }

        // Reseta o formulário e o modo de edição
        this.product = new Product();
        this.editMode = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }
    
    // MUDANÇA: O método de exclusão agora usa o 'selectedProduct'
    public void delete() {
        if (selectedProduct != null) {
            productService.deletebyObject(selectedProduct);
            products.remove(selectedProduct);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto excluído com sucesso!"));
            // Limpa a seleção
            selectedProduct = null;
        }
    }

    public void edit(Product productToEdit) {
        this.editMode = true;
        // Clona o objeto para evitar que alterações no formulário afetem a tabela antes de salvar
        this.product = productToEdit;
    }

    public void cancel() {
        this.editMode = false;
        this.product = new Product();
    }
}