<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <c:choose>
            <c:when test="${empty feedback.id}">Novo feedback</c:when>
            <c:otherwise>Editar feedback</c:otherwise>
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
            <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            <a href="${pageContext.request.contextPath}/feedbacks">Feedbacks</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>
            <c:choose>
                <c:when test="${empty feedback.id}">Novo feedback</c:when>
                <c:otherwise>Editar feedback</c:otherwise>
            </c:choose>
        </h1>
    </div>

    <div class="card">
        <c:if test="${not empty erro}">
            <div class="alert alert-erro">${erro}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/feedbacks">
            <input type="hidden" name="acao" value="salvar">
            <input type="hidden" name="id" value="${feedback.id}">

            <div class="form-group">
                <label for="produtoId">Produto</label>
                <select id="produtoId" name="produtoId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="produto" items="${produtos}">
                        <option value="${produto.id}"
                                <c:if test="${feedback.produtoId == produto.id}">selected</c:if>>
                            ${produto.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="usuarioId">Usuario</label>
                <select id="usuarioId" name="usuarioId" required>
                    <option value="">Selecione</option>
                    <c:forEach var="usuario" items="${usuarios}">
                        <option value="${usuario.id}"
                                <c:if test="${feedback.usuarioId == usuario.id}">selected</c:if>>
                            ${usuario.nome}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="nota">Nota (1 a 5)</label>
                <input type="number" min="1" max="5" step="1" id="nota" name="nota" value="${feedback.nota}" required>
            </div>

            <div class="form-group">
                <label for="comentario">Comentario (opcional)</label>
                <textarea id="comentario" name="comentario">${feedback.comentario}</textarea>
            </div>

            <div class="actions">
                <button type="submit" class="btn">Salvar</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/feedbacks">Cancelar</a>
            </div>
        </form>
    </div>
</main>
</body>
</html>
