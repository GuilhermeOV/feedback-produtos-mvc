<%--
  Pagina inicial: redireciona para a tela inicial (home).
--%>
<%
    response.sendRedirect(request.getContextPath() + "/home");
%>
