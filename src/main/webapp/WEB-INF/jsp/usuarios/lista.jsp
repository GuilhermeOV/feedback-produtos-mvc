<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Usuarios - Feedback</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <strong>Feedback de Produtos</strong>
        <nav>
            <a href="${pageContext.request.contextPath}/produtos">Produtos</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            <a href="${pageContext.request.contextPath}/feedbacks">Feedbacks</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>Usuarios</h1>
        <a class="btn" href="${pageContext.request.contextPath}/usuarios?acao=novo">Novo usuario</a>
    </div>

    <c:if test="${not empty erro}">
        <div class="alert alert-erro">${erro}</div>
    </c:if>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty usuarios}">
                <p class="empty">Nenhum usuario cadastrado.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr>
                            <td>${usuario.id}</td>
                            <td>${usuario.nome}</td>
                            <td>${usuario.email}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/usuarios?acao=editar&id=${usuario.id}">Editar</a>
                                <a href="${pageContext.request.contextPath}/usuarios?acao=excluir&id=${usuario.id}"
                                   onclick="return confirm('Excluir este usuario?');">Excluir</a>
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
