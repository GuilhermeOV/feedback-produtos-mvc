# Roteiro de Testes — Aplicativo de Feedback para Produtos

Este documento descreve os testes manuais realizados para validar as funcionalidades do
sistema, garantindo que o CRUD e as regras de negócio funcionam como esperado.

## Ambiente de teste
- Aplicação executada via Docker (`docker compose up -d`).
- Acesso pelo navegador em `http://127.0.0.1:8080/feedback/`.

## Legenda
- ✅ = resultado esperado obtido.

---

## 1. Produtos

| # | Ação | Passos | Resultado esperado |
|---|------|--------|--------------------|
| 1.1 | Listar | Acessar "Produtos" | Exibe a tabela com os produtos cadastrados ✅ |
| 1.2 | Cadastrar | "Novo produto" → preencher nome, descrição e preço → Salvar | Produto aparece na lista ✅ |
| 1.3 | Editar | "Editar" em um produto → alterar dados → Salvar | Dados atualizados na lista ✅ |
| 1.4 | Excluir (sem feedback) | "Excluir" em um produto sem feedback | Produto é removido ✅ |
| 1.5 | Excluir (com feedback) | "Excluir" em um produto que possui feedback | Exibe mensagem "Nao e possivel excluir: existe feedback vinculado a este produto" ✅ |
| 1.6 | Campo obrigatório | Tentar salvar sem nome | Formulário não permite / exibe erro ✅ |

## 2. Usuários

| # | Ação | Passos | Resultado esperado |
|---|------|--------|--------------------|
| 2.1 | Listar | Acessar "Usuarios" | Exibe a tabela com os usuários ✅ |
| 2.2 | Cadastrar | "Novo usuario" → preencher nome e email → Salvar | Usuário aparece na lista ✅ |
| 2.3 | Editar | "Editar" → alterar dados → Salvar | Dados atualizados ✅ |
| 2.4 | E-mail repetido | Cadastrar usuário com e-mail já existente | Exibe "Ja existe um usuario com este email" ✅ |
| 2.5 | Excluir | "Excluir" em um usuário sem feedback | Usuário é removido ✅ |

## 3. Feedbacks

| # | Ação | Passos | Resultado esperado |
|---|------|--------|--------------------|
| 3.1 | Listar | Acessar "Feedbacks" | Exibe a tabela mostrando nome do produto e do usuário ✅ |
| 3.2 | Cadastrar | "Novo feedback" → escolher produto e usuário, nota e comentário → Salvar | Feedback aparece na lista ✅ |
| 3.3 | Editar | "Editar" → alterar nota/comentário → Salvar | Dados atualizados ✅ |
| 3.4 | Nota inválida | Informar nota fora do intervalo (ex.: 9) | Exibe "Nota deve ser de 1 a 5" ✅ |
| 3.5 | Excluir | "Excluir" em um feedback | Feedback é removido ✅ |

## 4. Navegação
| # | Ação | Resultado esperado |
|---|------|--------------------|
| 4.1 | Acessar a raiz `/feedback/` | Redireciona para a tela inicial (Home) ✅ |
| 4.2 | Usar o menu superior | Navega entre Início, Produtos, Usuários e Feedbacks ✅ |

## Conclusão
Todas as funcionalidades de CRUD e as regras de negócio (chave estrangeira, e-mail único e
intervalo da nota) foram testadas e apresentaram o comportamento esperado.
