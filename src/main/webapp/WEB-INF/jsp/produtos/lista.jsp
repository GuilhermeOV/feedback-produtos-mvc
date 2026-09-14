<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Produtos - Feedback</title>
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
        <h1>Produtos</h1>
        <a class="btn" href="${pageContext.request.contextPath}/produtos?acao=novo">Novo produto</a>
    </div>

    <c:if test="${not empty erro}">
        <div class="alert alert-erro">${erro}</div>
    </c:if>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty produtos}">
                <p class="empty">Nenhum produto cadastrado.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descricao</th>
                        <th>Preco (R$)</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="produto" items="${produtos}">
                        <tr>
                            <td>${produto.id}</td>
                            <td>${produto.nome}</td>
                            <td>${produto.descricao}</td>
                            <td>${produto.preco}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/produtos?acao=editar&id=${produto.id}">Editar</a>
                                <a href="${pageContext.request.contextPath}/produtos?acao=excluir&id=${produto.id}"
                                   onclick="return confirm('Excluir este produto?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>
