package com.manager.foodmn.menu;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@ApplicationScoped
@Getter
public class MenuController {

    private List<MenuItem> menuItems;

    @PostConstruct
    public void init() {
        menuItems = new ArrayList<>();
        // Adiciona a página Home estaticamente
        menuItems.add(new MenuItem("Home", "/index.xhtml"));

        try {
            // Pega o caminho real para a raiz da aplicação web
            String webappRoot = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File rootDir = new File(webappRoot);

            // Lista todos os arquivos .xhtml na raiz
            File[] files = rootDir.listFiles((dir, name) -> name.endsWith(".xhtml"));

            if (files != null) {
                Arrays.stream(files)
                    // Filtra para não adicionar a home novamente ou templates
                    .filter(file -> !file.getName().equals("index.xhtml"))
                    .forEach(file -> {
                        String fileName = file.getName();
                        // Cria um rótulo amigável (ex: "produto.xhtml" -> "Produto")
                        String label = fileName.substring(0, 1).toUpperCase() + fileName.substring(1).replace(".xhtml", "");
                        String url = "/" + fileName;
                        menuItems.add(new MenuItem(label, url));
                    });
            }
        } catch (Exception e) {
            // Em caso de erro (ex: contexto do Faces não disponível), loga o erro.
            // Em um ambiente de produção, seria ideal usar um logger.
            System.err.println("Erro ao gerar menu dinâmico: " + e.getMessage());
        }
    }
}