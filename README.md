


# SpringBootJSF-ANTLR

Este projeto é uma aplicação web desenvolvida com **Spring Boot**, **Jakarta Server Faces (JSF)** e **ANTLR**.  
A principal funcionalidade é um **gerador de código** que utiliza ANTLR para criar um CRUD completo (Model, Repository, Service, Controller e View JSF) a partir de arquivos de definição de entidade personalizados.

---

## ✨ Funcionalidades

- **Geração de Código com ANTLR**: Cria automaticamente toda a estrutura de CRUD para novas entidades a partir de arquivos de definição simples (`.ent`).
- **CRUD de Produtos**: Inclui um exemplo funcional para a entidade `Produto`, que pode ser usado como referência.
- **Backend com Spring Boot**: Utiliza Spring Data JPA e uma estrutura robusta baseada em boas práticas.
- **Frontend com JSF**: Interface construída com JSF, integrada ao Spring Boot por meio do JoinFaces.

---

## 🧰 Tecnologias Utilizadas

### Backend

- Java 17  
- Spring Boot 3.3.5  
- Spring Web  
- Spring Data JPA  
- Hibernate  
- Lombok  

### Frontend

- Jakarta Server Faces (JSF)  
- JoinFaces 5.3.0  

### Banco de Dados

- PostgreSQL  

### Build & Dependências

- Maven  

### Geração de Código

- ANTLR 4.13.1  

---

## 🚀 Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone <https://github.com/joardam/SpringBootJSF-ANTLR>
cd springbootjsf-antlr
````

### 2. Configure o Banco de Dados

* Certifique-se de que o **PostgreSQL** está instalado e em execução.
* Crie um banco de dados chamado `springbootjsf_antlr`.
* Ajuste o arquivo `src/main/resources/application.properties` com sua URL, usuário e senha, se forem diferentes do padrão (`postgres/postgres`).

### 3. Execute a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## ⚙️ Geração de Código com ANTLR

O coração do projeto é o **gerador de entidades**. Ele lê arquivos com a extensão `.ent` localizados em `src/main/resources/entities`.

### 1. Crie um Arquivo de Definição

Por exemplo, para criar a entidade `Category`, crie o arquivo:

```bash
src/main/resources/entities/Category.ent
```

Com o conteúdo:

```antlr
entity Category {
    name: String
}
```

### 2. Execute o Gerador

Execute a classe:

```java
com.manager.foodmn.generator.EntityGenerator
```

### 3. Estrutura Gerada

O gerador criará os seguintes arquivos para a nova entidade:

* **Model**:
  `src/main/java/com/manager/foodmn/category/model/Category.java`

* **Repository**:
  `src/main/java/com/manager/foodmn/category/repository/CategoryRepository.java`

* **Service (Interface e Implementação)**:
  `src/main/java/com/manager/foodmn/category/service/`

* **Controller**:
  `src/main/java/com/manager/foodmn/category/controller/CategoryController.java`

* **View (JSF)**:
  `src/main/webapp/category.xhtml`

> Após a geração, **reinicie a aplicação** para que as novas tabelas sejam criadas e a nova página esteja acessível.

---

## 📁 Estrutura do Projeto

```
springbootjsf-antlr/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/manager/foodmn/
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── entities/
│   │   │       └── Category.ent
│   │   └── webapp/
│   │       └── category.xhtml
├── pom.xml
```

---



