<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <c:choose>
            <c:when test="${empty produto.id}">Novo produto</c:when>
            <c:otherwise>Editar produto</c:otherwise>
        </c:choose>
        - Feedback
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <strong>Feedback de Produtos</strong>
        <nav>
            <a href="${pageContext.request.contextPath}/produtos">Produtos</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>
            <c:choose>
                <c:when test="${empty produto.id}">Novo produto</c:when>
                <c:otherwise>Editar produto</c:otherwise>
            </c:choose>
        </h1>
    </div>

    <div class="card">
        <c:if test="${not empty erro}">
            <div class="alert alert-erro">${erro}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/produtos">
            <input type="hidden" name="acao" value="salvar">
            <input type="hidden" name="id" value="${produto.id}">

            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" value="${produto.nome}" required>
            </div>

            <div class="form-group">
                <label for="descricao">Descricao</label>
                <textarea id="descricao" name="descricao" required>${produto.descricao}</textarea>
            </div>

            <div class="form-group">
                <label for="preco">Preco (R$)</label>
                <input type="number" step="0.01" min="0" id="preco" name="preco" value="${produto.preco}" required>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/produtos">Cancelar</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
