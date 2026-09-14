<%--
  Pagina inicial: redireciona para a lista de produtos.
--%>
<%
    response.sendRedirect(request.getContextPath() + "/produtos");
%>
