# Aplicativo de Feedback para Produtos

Trabalho da disciplina **Aplicações para Internet**, desenvolvido em **Java** com o padrão de
arquitetura **MVC (Model-View-Controller)**, além dos padrões **DAO** e **Service**.

O sistema permite que usuários enviem **feedback** (uma nota de 1 a 5 e um comentário) sobre
**produtos** cadastrados. Ele implementa **CRUD completo** (Criar, Ler, Atualizar e Excluir)
para as três entidades do projeto.

## 👥 Dupla

- Guilherme Vital — 5160116
- Vitor Gomes — 5160058

## 🛠️ Tecnologias utilizadas

- **Java 17**
- **Jakarta Servlets 6.0** e **JSP + JSTL** (camada de View)
- **Maven** (gerenciamento de dependências e build do arquivo `.war`)
- **MySQL 8.4** (banco de dados)
- **Apache Tomcat 10.1** (servidor de aplicação)
- **Docker / Docker Compose** (sobe o banco e o servidor automaticamente)

## 🗂️ Estrutura do projeto (padrão MVC + DAO + Service)

```
feedback/
├── docker-compose.yml          # Sobe o MySQL e o Tomcat juntos
├── init.sql                    # Cria o banco e as 3 tabelas + dados de exemplo
├── pom.xml                     # Configuracao do Maven (dependencias e build)
└── src/main/
    ├── java/br/com/feedback/
    │   ├── config/
    │   │   └── MysqlSingleton.java     # Conexao unica com o banco (padrao Singleton)
    │   ├── model/                      # MODEL — espelham as tabelas do banco
    │   │   ├── Produto.java
    │   │   ├── Usuario.java
    │   │   └── Feedback.java
    │   ├── dao/                        # DAO — acesso aos dados (somente SQL)
    │   │   ├── MysqlDAO.java           # Classe base dos DAOs
    │   │   ├── ProdutoDAO.java
    │   │   ├── UsuarioDAO.java
    │   │   └── FeedbackDAO.java
    │   ├── service/                    # SERVICE — regras de negocio / validacoes
    │   │   ├── ProdutoService.java
    │   │   ├── UsuarioService.java
    │   │   └── FeedbackService.java
    │   └── controller/                 # CONTROLLER — Servlets (rotas)
    │       ├── BaseServlet.java        # Classe base dos controllers
    │       ├── HomeServlet.java
    │       ├── ProdutoServlet.java
    │       ├── UsuarioServlet.java
    │       └── FeedbackServlet.java
    └── webapp/
        ├── index.jsp                   # Redireciona para a tela inicial
        ├── css/estilo.css              # Estilo das telas
        └── WEB-INF/
            ├── web.xml
            └── jsp/                     # VIEW — telas do sistema
                ├── home.jsp
                ├── produtos/    (lista.jsp, form.jsp)
                ├── usuarios/    (lista.jsp, form.jsp)
                └── feedbacks/   (lista.jsp, form.jsp)
```

## 🧠 Como o padrão MVC foi aplicado

O caminho de qualquer ação segue sempre as mesmas camadas, cada uma com uma responsabilidade:

```
Navegador  ->  Controller (Servlet)  ->  Service (regras)  ->  DAO (SQL)  ->  Banco de dados
                       |
                       v
                  View (JSP) mostra o resultado
```

- **Model** (`model/`): classes simples que representam os dados (Produto, Usuario, Feedback).
- **View** (`webapp/WEB-INF/jsp/`): páginas JSP que montam o HTML dinâmico com JSTL.
- **Controller** (`controller/`): Servlets que recebem a requisição, chamam o Service e escolhem a View.
- **DAO** (`dao/`): única camada que executa SQL no banco.
- **Service** (`service/`): concentra as regras de negócio e validações.

Exemplo prático (excluir um produto): o `ProdutoServlet` recebe a rota, chama
`ProdutoService.deletar()`, que valida a regra e chama `ProdutoDAO.deletar()`, que executa o
`DELETE`. Por fim o Controller redireciona para a lista, e a View exibe o resultado.

## 🗄️ Banco de dados

Três tabelas, criadas automaticamente pelo `init.sql`:

- **produtos** (`id`, `nome`, `descricao`, `preco`)
- **usuarios** (`id`, `nome`, `email` único)
- **feedback** (`id`, `usuario_id`, `produto_id`, `nota`, `comentario`)

A tabela `feedback` possui **chaves estrangeiras** para `usuarios` e `produtos`, garantindo a
integridade dos dados (não é possível registrar feedback de um produto/usuário inexistente, nem
excluir um produto/usuário que ainda tenha feedback vinculado).

## ▶️ Como executar o projeto

### Pré-requisitos
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e em execução.

### Passos
1. Clonar o repositório:
   ```bash
   git clone https://github.com/GuilhermeOV/feedback-produtos-mvc.git
   cd feedback-produtos-mvc
   ```
2. Gerar o arquivo `.war` (se tiver o Maven instalado):
   ```bash
   mvn clean package
   ```
3. Subir o banco e o servidor com Docker:
   ```bash
   docker compose up -d
   ```
4. Acessar no navegador:
   ```
   http://127.0.0.1:8080/feedback/
   ```

Para encerrar: `docker compose down`.

## ✅ Funcionalidades

- CRUD completo de **Produtos**, com tratamento de chave estrangeira na exclusão.
- CRUD completo de **Usuários**, com validação de **e-mail único**.
- CRUD completo de **Feedbacks**, relacionando produto + usuário (via `INNER JOIN`) e validando
  a **nota (1 a 5)** e a existência do produto e do usuário.
- Tela inicial (Home) com atalhos e navegação entre os cadastros.
- Mensagens de erro amigáveis exibidas na própria tela.
