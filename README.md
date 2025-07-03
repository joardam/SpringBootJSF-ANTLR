# Spring Boot, JSF & ANTLR: Gerador de CRUD Dinâmico

Este projeto é uma aplicação web robusta que demonstra o poder da combinação entre **Spring Boot**, **Jakarta Server Faces (JSF)** com **PrimeFaces** e **ANTLR**. Sua principal funcionalidade é um gerador de código que, a partir de arquivos de definição de entidade simples, cria um CRUD (Create, Read, Update, Delete) completo e funcional, incluindo **Model**, **Repository**, **Service**, **Controller** e a **View** com PrimeFaces.

O sistema já vem com um CRUD de **Produto** como exemplo funcional e um menu dinâmico que se atualiza automaticamente a cada nova entidade gerada.

---

## 🚀 Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate
* **Frontend:** Jakarta Server Faces (JSF), PrimeFaces, JoinFaces
* **Banco de Dados:** PostgreSQL
* **Geração de Código:** ANTLR 4
* **Build:** Maven
* **Ambiente:** Otimizado para GitHub Codespaces & Docker

---

## ☁️ Guia de Início Rápido com GitHub Codespaces

Este projeto é pré-configurado para funcionar perfeitamente com o GitHub Codespaces, que cria um ambiente de desenvolvimento completo e pronto para uso no seu navegador.

1. **Abra no Codespaces**

   * Na página principal do seu repositório no GitHub, clique no botão verde **"<> Code"**, vá para a aba **"Codespaces"** e clique em **"Create codespace on main"**.

2. **Aguarde a Mágica**
   O Codespaces irá automaticamente:

   * Montar um contêiner Docker com Java, Maven e PostgreSQL.
   * Instalar todas as dependências do Maven (`mvn install`).
   * Disponibilizar o terminal do VS Code no seu navegador.

3. **Rode a Aplicação**

   * Abra um novo terminal no Codespace e execute:

     ```bash
     mvn spring-boot:run
     ```

4. **Acesse a Aplicação**

   * O Codespaces detectará que a porta **8080** está em uso e fará o encaminhamento automaticamente. Uma notificação aparecerá no canto inferior direito da tela. Clique em **"Abrir no Navegador"** para ver sua aplicação funcionando.

5. **Resolvendo Erro 401 (“Unauthorized”) no Codespaces**
   
   Caso a aplicação retorne um **Erro 401** ao acessar endpoints protegidos, pode ser que a porta não esteja visível externamente. Siga estes passos:

   1. No canto inferior direito do Codespaces, clique no ícone **"Ports"** (🔌).
   2. Localize a porta **8080** na lista (ou outra porta onde sua aplicação esteja rodando).
   3. Clique clique com botão direito do mouse em **Visibilidade da Porta**.
   4. modifique para **"Public"**.
   5. Recarregue a página no navegador ou copie o link público gerado e abra-o.
   6. Sua aplicação deve responder sem o erro 401.

---

## ⚙️ Gerando um Novo CRUD

O coração do projeto é o gerador de código. Siga os passos abaixo para criar um CRUD completo para uma nova entidade (ex: `Categoria`).

### 1. Crie o Arquivo de Definição da Entidade

* **Localização:** `src/main/resources/entities/`
* **Exemplo:** `src/main/resources/entities/Categoria.ent`

```ent
entity Categoria {
    String nome
    String descricao
}
```

**Regras:**

* A primeira linha deve ser `entity NomeDaEntidade {`.
* O campo `id` é gerado automaticamente.
* Cada campo é definido como `TipoDeDado nomeDoCampo`.
* Tipos de dados aceitos: `String`, `Integer`, `Double`, `BigDecimal`.

### 2. Execute o Gerador de Código

Para transformar o arquivo `.ent` em código Java e XHTML, execute a classe `EntityGenerator` via Maven, usando o perfil `generate-entities` configurado no `pom.xml`:

```bash
mvn compile exec:java -P generate-entities
```

O terminal exibirá a criação de todos os arquivos: Model, Repository, Service (Interface e Impl), Controller e a View `.xhtml`.

### 3. Reinicie a Aplicação

* Pare a aplicação que estava rodando (Ctrl + C).
* Inicie-a novamente:

```bash
mvn spring-boot:run
```

Ao acessar a aplicação no navegador, o novo item **"Categoria"** aparecerá automaticamente no menu, e a página estará pronta para uso.

---

## 📦 Build do Projeto

Para empacotar a aplicação em um arquivo `.war` (pronto para deploy em servidores como o Tomcat), utilize:

```bash
mvn clean package
```

O arquivo final estará em `target/food-manager-0.0.1-SNAPSHOT.war`.

---

## 📁 Estrutura do Projeto

```
├── .devcontainer/      # Configuração do Docker e Codespaces
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/manager/foodmn
│   │   │       ├── config/         # Configurações do Spring (ex: redirect)
│   │   │       ├── domain/         # Entidades geradas (ex: produto/)
│   │   │       ├── generator/      # O gerador de código
│   │   │       └── menu/           # Controller do menu dinâmico
│   │   ├── resources
│   │   │   ├── application.properties
│   │   │   └── entities/           # >> Arquivos .ent aqui
│   │   └── webapp/
│   │       ├── templates/          # Layout principal com o menu
│   │       ├── index.xhtml         # Página inicial
│   │       └── produto.xhtml       # View de exemplo gerada
│   └── test/
├── pom.xml             # Configuração do Maven
└── README.md           # Esta documentação
```
