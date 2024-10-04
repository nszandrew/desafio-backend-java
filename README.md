# API - Desafio Backend Java

Este repositório contém a API desenvolvida como parte do desafio técnico de desenvolvimento. A aplicação é uma API RESTful construída em Java utilizando o framework Spring Boot. Ela fornece endpoints para o gerenciamento de usuários, incluindo cadastro, login, atualização, obtenção e exclusão de usuários.

## Índice

- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Executando o Projeto](#executando-o-projeto)
  - [Executando Localmente (Sem Docker)](#executando-localmente-sem-docker)
  - [Executando com Docker](#executando-com-docker)
- [Documentação da API](#documentação-da-api)
  - [Usando o Swagger UI](#usando-o-swagger-ui)
  - [Usando a Coleção do Postman](#usando-a-coleção-do-postman)
- [Endpoints da API](#endpoints-da-api)
- [Contato](#contato)

## Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- **Java 17**
- **Maven 3.6+**
- **Docker** (opcional, se desejar executar com Docker)
- **Postman** (opcional, para utilizar a coleção disponibilizada)

## Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/nszandrew/desafio-backend-java.git
   ```

2. **Compile o projeto usando Maven:**

   ```bash
   mvn clean install
   ```

## Executando o Projeto

### Executando Localmente (Sem Docker)

1. **Executar na sua IDE de preferencia o IntelliJ ou VSCode**

    Clicando no icone de íniciar a aplicação:

   A aplicação estará disponível em `http://localhost:8080`. Certifique-se de não haver outro software rodando na porta 8080.

### Executando com Docker

1. **Certifique-se de que o Docker esta instalado.**

2. **Abra o CMD na pasta que está o Dockerfile**

3. **Inicie os contêineres:** 

   ```bash
   docker build -t api-spring .
   ```
4. **Verifique se a imagem está baixada e copie o id da imagem:** 

   ```bash
   docker images
   ```
   ![image](https://github.com/user-attachments/assets/f1d2700f-ed66-4b23-b5f1-93b712d959a9)

5. **Verifique se a imagem está baixada e pegue o id da imagem:** 

   ```
   docker run -p 8080:8080 {imagem que voce copiou}
   ```

    Isso irá:

   - Construir a imagem Docker da aplicação.
   - Iniciar os contêineres da aplicação na porta 8080.

6. **Verifique se a aplicação está rodando:**

   Acesse `http://localhost:8080` no seu navegador ou ferramentas da API como o Postmann collection.json na pasta raiz da aplicação.

## Documentação da API

A API está documentada usando Swagger/OpenAPI, permitindo que você explore e teste os endpoints facilmente.

### Usando o Swagger UI

1. **Acesse o Swagger UI:**

   - Abra o navegador e vá para `http://localhost:8080/swagger-ui/index.html#/` ou `http://localhost:8080/swagger-ui/`.

2. **Explore os Endpoints:**

   - Visualize a documentação dos endpoints.
   - Teste as requisições diretamente através da interface.

### Usando a Coleção do Postman

1. **Importe a Coleção:**

   - Na raiz do projeto, você encontrará o arquivo `API Desafio Backend Java - nszandrew Copy.postman_collection.json`.
   - Abra o Postman e clique em **Importar**.
   - Selecione o arquivo da coleção.

2. **Utilize a Coleção:**

   - A coleção importada contém todos os endpoints da API.
   - Ajuste as variáveis conforme necessário.

## Endpoints da API

Abaixo está um resumo dos principais endpoints disponíveis na API:

- **Registrar Usuário**

  - **Método:** `POST`
  - **Endpoint:** `/api/user/registro`
  - **Descrição:** Cria um novo usuário.
  - **Corpo da Requisição:**

    ```json
    {
      "nome": "Seu Nome",
      "email": "seu.email@example.com",
      "senha": "sua_senha"
    }
    ```

- **Login de Usuário**

  - **Método:** `POST`
  - **Endpoint:** `/api/user/login`
  - **Descrição:** Autentica um usuário e retorna um token JWT.
  - **Corpo da Requisição:**

    ```json
    {
      "email": "seu.email@example.com",
      "senha": "sua_senha"
    }
    ```

- **Atualizar Usuário**

  - **Método:** `PUT`
  - **Endpoint:** `/api/user/atualizar/{id}`
  - **Descrição:** Atualiza as informações de um usuário existente.
  - **Cabeçalho de Autenticação:**

    ```
    Authorization: Bearer {seu_token_jwt}
    ```

  - **Corpo da Requisição:**

    ```json
    {
      "nome": "Novo Nome",
      "email": "novo.email@example.com",
      "senha": "nova_senha"
    }
    ```

- **Obter Usuário por ID**

  - **Método:** `GET`
  - **Endpoint:** `/api/user/get/{id}`
  - **Descrição:** Obtém as informações de um usuário pelo ID.
  - **Cabeçalho de Autenticação:**

    ```
    Authorization: Bearer {seu_token_jwt}
    ```

- **Deletar Usuário**

  - **Método:** `DELETE`
  - **Endpoint:** `/api/user/deletar/{id}`
  - **Descrição:** Remove um usuário pelo ID.
  - **Cabeçalho de Autenticação:**

    ```
    Authorization: Bearer {seu_token_jwt}
    ```

## Contato

Desenvolvido por Andrew Nunes Souza.

- **Website:** [nszandrew.com.br](http://nszandrew.com.br)
- **Email:** [nszandrew10@gmail.com](mailto:nszandrew10@gmail.com)
