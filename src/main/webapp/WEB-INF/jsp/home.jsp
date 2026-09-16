<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inicio - Feedback de Produtos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <strong>Feedback de Produtos</strong>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Inicio</a>
            <a href="${pageContext.request.contextPath}/produtos">Produtos</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            <a href="${pageContext.request.contextPath}/feedbacks">Feedbacks</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>Bem-vindo ao Aplicativo de Feedback para Produtos</h1>
    </div>

    <p style="color:#6b7280; margin-top:-0.5rem; margin-bottom:1.5rem;">
        Sistema onde usuarios enviam feedback (nota e comentario) para produtos.
        Escolha um cadastro abaixo para comecar.
    </p>

    <div class="grid-cards">
        <a class="menu-card" href="${pageContext.request.contextPath}/produtos">
            <strong>Produtos</strong>
            <span>Cadastrar, listar, editar e excluir produtos.</span>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/usuarios">
            <strong>Usuarios</strong>
            <span>Cadastrar, listar, editar e excluir usuarios.</span>
        </a>
        <a class="menu-card" href="${pageContext.request.contextPath}/feedbacks">
            <strong>Feedbacks</strong>
            <span>Registrar o feedback de um usuario sobre um produto.</span>
        </a>
    </div>
</main>
</body>
</html>
