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

   - Na raiz do projeto, você encontrará o arquivo `API Desafio Backend Java - nszandrew.postman_collection.json`. https://github.com/nszandrew/desafio-backend-java/blob/main/API%20Desafio%20Backend%20Java%20-%20nszandrew.postman_collection.json
   - Abra o Postman e clique em **Importar**.
   - Selecione o arquivo da coleção.

2. **Utilize a Coleção:**

   - A coleção importada contém todos os endpoints da API.
   - Ajuste as variáveis conforme necessário.


## Endpoints da API

### Criar um novo usuário
`POST /api/user/registro`
- **Descrição:** Cria um novo usuário no banco de dados.
- **Requisição:** 
  ```json
  {
    "nome": "string",
    "email": "string",
    "senha": "string"
  }
  ```
- **Respostas:**
  - `201`: Usuário criado com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `403`: Token não autorizado.
  - `409`: Email já cadastrado.

### Atualizar usuário
`PUT /api/user/atualizar/{id}`
- **Descrição:** Atualiza as informações de um usuário existente pelo ID.
- **Parâmetros:**
  - `id`: ID do usuário a ser atualizado.
- **Requisição:**
  ```json
  {
    "nome": "string",
    "email": "string"
  }
  ```
- **Respostas:**
  - `200`: Usuário atualizado com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `403`: Token não autorizado.

### Deletar usuário
`DELETE /api/user/deletar/{id}`
- **Descrição:** Deleta um usuário pelo ID.
- **Parâmetros:**
  - `id`: ID do usuário a ser deletado.
- **Respostas:**
  - `204`: Usuário deletado com sucesso.
  - `403`: Token não autorizado.

### Buscar usuário por ID
`GET /api/user/get/{id}`
- **Descrição:** Recupera um usuário pelo ID.
- **Parâmetros:**
  - `id`: ID do usuário.
- **Respostas:**
  - `200`: Usuário recuperado com sucesso.
  - `403`: Token não autorizado.

### Login de usuário
`POST /api/user/login`
- **Descrição:** Realiza o login de um usuário com email e senha.
- **Requisição:**
  ```json
  {
    "email": "string",
    "senha": "string"
  }
  ```
- **Respostas:**
  - `200`: Login realizado com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `403`: Token não autorizado.

---

## Endpoints de Projetos

### Criar um novo projeto
`POST /api/projeto`
- **Descrição:** Cria um novo projeto.
- **Requisição:**
  ```json
  {
    "nome": "string",
    "descricao": "string"
  }
  ```
- **Respostas:**
  - `201`: Projeto criado com sucesso.
  - `400`: Erro de validação nos dados enviados.

### Atualizar projeto
`PUT /api/projeto/{id}`
- **Descrição:** Atualiza um projeto existente pelo ID.
- **Parâmetros:**
  - `id`: ID do projeto a ser atualizado.
- **Requisição:**
  ```json
  {
    "nome": "string",
    "descricao": "string"
  }
  ```
- **Respostas:**
  - `200`: Projeto atualizado com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `404`: Projeto não encontrado.

### Excluir projeto
`DELETE /api/projeto/{id}`
- **Descrição:** Exclui um projeto pelo ID.
- **Parâmetros:**
  - `id`: ID do projeto a ser excluído.
- **Respostas:**
  - `204`: Projeto excluído com sucesso.
  - `404`: Projeto não encontrado.

### Buscar projeto por ID
`GET /api/projeto/{id}`
- **Descrição:** Recupera um projeto pelo ID.
- **Parâmetros:**
  - `id`: ID do projeto.
- **Respostas:**
  - `200`: Projeto recuperado com sucesso.
  - `404`: Projeto não encontrado.

---

## Endpoints de Metas

### Criar uma nova meta
`POST /api/metas/{id}`
- **Descrição:** Cria uma nova meta para um projeto existente.
- **Parâmetros:**
  - `id`: ID do projeto ao qual a meta será adicionada.
- **Requisição:**
  ```json
  {
    "descricao": "string",
    "completada": "boolean",
    "dataLimite": "string (YYYY-MM-DD)"
  }
  ```
- **Respostas:**
  - `201`: Meta criada com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `404`: Projeto não encontrado.

### Atualizar meta
`PUT /api/metas/{id}`
- **Descrição:** Atualiza uma meta existente pelo ID.
- **Parâmetros:**
  - `id`: ID da meta a ser atualizada.
- **Requisição:**
  ```json
  {
    "descricao": "string",
    "completada": "boolean",
    "dataLimite": "string (YYYY-MM-DD)"
  }
  ```
- **Respostas:**
  - `200`: Meta atualizada com sucesso.
  - `400`: Erro de validação nos dados enviados.
  - `404`: Meta não encontrada.

### Excluir meta
`DELETE /api/metas/{id}`
- **Descrição:** Exclui uma meta pelo ID.
- **Parâmetros:**
  - `id`: ID da meta a ser excluída.
- **Respostas:**
  - `204`: Meta excluída com sucesso.
  - `404`: Meta não encontrada.

### Buscar meta por ID
`GET /api/metas/{id}`
- **Descrição:** Recupera uma meta pelo ID.
- **Parâmetros:**
  - `id`: ID da meta.
- **Respostas:**
  - `200`: Meta recuperada com sucesso.
  - `404`: Meta não encontrada.

---

## Segurança
Todos os endpoints são protegidos por autenticação JWT. Para acessar a API, você deve incluir o token no cabeçalho da requisição:

```
Authorization: Bearer <token>
```
![image](https://github.com/user-attachments/assets/0a93b15a-5e13-408f-b6f8-e3cb917856b7)



## Testes

Este projeto contém testes unitários para garantir a funcionalidade correta dos endpoints e da lógica de negócios.

### Como rodar os testes:

Para rodar os testes, você pode usar o Maven com o seguinte comando:

```bash
mvn test
```


## Contato

Desenvolvido por Andrew Nunes Souza.

- **Website:** [nszandrew.com.br](http://nszandrew.com.br)
- **Email:** [nszandrew10@gmail.com](mailto:nszandrew10@gmail.com)
