package br.com.feedback.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Classe BASE dos controllers (Servlets).
 *
 * So contem "ajudantes" de rota/tela: ler parametro, encaminhar para uma JSP
 * e redirecionar. A REGRA DE NEGOCIO nunca fica aqui — fica no Service.
 */
public abstract class BaseServlet extends HttpServlet {

    /** Le o parametro "acao" da URL. Se nao vier, assume "listar". */
    protected String acao(HttpServletRequest req) {
        String acao = req.getParameter("acao");
        if (acao == null || acao.isBlank()) {
            return "listar";
        }
        return acao;
    }

    /** Le um parametro de texto simples. */
    protected String param(HttpServletRequest req, String nome) {
        return req.getParameter(nome);
    }

    /** Le um parametro e converte para numero (Long). Se estiver vazio/invalido, retorna null. */
    protected Long paramLong(HttpServletRequest req, String nome) {
        String valor = req.getParameter(nome);
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return Long.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Encaminha para uma pagina JSP (mostra a tela). */
    protected void forward(HttpServletRequest req, HttpServletResponse resp, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    /** Redireciona o navegador para outra rota da aplicacao. */
    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String caminho)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + caminho);
    }
}
