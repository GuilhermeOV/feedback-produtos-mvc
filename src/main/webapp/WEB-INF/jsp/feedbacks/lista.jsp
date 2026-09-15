<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Feedbacks - Feedback</title>
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
        <h1>Feedbacks</h1>
        <a class="btn" href="${pageContext.request.contextPath}/feedbacks?acao=novo">Novo feedback</a>
    </div>

    <c:if test="${not empty erro}">
        <div class="alert alert-erro">${erro}</div>
    </c:if>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty feedbacks}">
                <p class="empty">Nenhum feedback cadastrado.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Produto</th>
                        <th>Usuario</th>
                        <th>Nota</th>
                        <th>Comentario</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="feedback" items="${feedbacks}">
                        <tr>
                            <td>${feedback.id}</td>
                            <td>${feedback.produto.nome}</td>
                            <td>${feedback.usuario.nome}</td>
                            <td>${feedback.nota}</td>
                            <td>${feedback.comentario}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/feedbacks?acao=editar&id=${feedback.id}">Editar</a>
                                <a href="${pageContext.request.contextPath}/feedbacks?acao=excluir&id=${feedback.id}"
                                   onclick="return confirm('Excluir este feedback?');">Excluir</a>
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
