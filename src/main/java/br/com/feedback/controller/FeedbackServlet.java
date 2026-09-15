package br.com.feedback.controller;

import br.com.feedback.model.Feedback;
import br.com.feedback.service.FeedbackService;
import br.com.feedback.service.ProdutoService;
import br.com.feedback.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * CONTROLLER de Feedback.
 * Ponte entre a rota (/feedbacks), o {@link FeedbackService} e as telas (JSP).
 * Tambem usa ProdutoService e UsuarioService para popular os selects do formulario.
 */
@WebServlet("/feedbacks")
public class FeedbackServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/feedbacks/lista.jsp";
    private static final String FORM = "/WEB-INF/jsp/feedbacks/form.jsp";

    private final FeedbackService feedbackService = new FeedbackService();
    private final ProdutoService produtoService = new ProdutoService();
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {
            case "novo" -> this.form(req, resp, null);
            case "editar" -> this.form(req, resp, this.feedbackService.buscarPorId(this.paramLong(req, "id")));
            case "excluir" -> {
                try {
                    this.feedbackService.deletar(this.paramLong(req, "id"));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("erro", e.getMessage());
                    req.setAttribute("feedbacks", this.feedbackService.listar());
                    this.forward(req, resp, LISTA);
                    return;
                }
                this.redirect(req, resp, "/feedbacks");
            }
            default -> {
                req.setAttribute("feedbacks", this.feedbackService.listar());
                this.forward(req, resp, LISTA);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Feedback feedback = this.fromRequest(req);

        try {
            this.feedbackService.salvar(feedback);
            this.redirect(req, resp, "/feedbacks");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            this.form(req, resp, feedback);
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Feedback feedback)
            throws ServletException, IOException {

        if ("editar".equals(this.acao(req)) && feedback == null) {
            this.redirect(req, resp, "/feedbacks");
            return;
        }

        req.setAttribute("feedback", feedback);
        req.setAttribute("produtos", this.produtoService.listar());
        req.setAttribute("usuarios", this.usuarioService.listar());
        this.forward(req, resp, FORM);
    }

    private Feedback fromRequest(HttpServletRequest req) {
        Feedback feedback = new Feedback();
        feedback.setId(this.paramLong(req, "id"));
        feedback.setProdutoId(this.paramLong(req, "produtoId"));
        feedback.setUsuarioId(this.paramLong(req, "usuarioId"));
        feedback.setNota(this.parseInt(this.param(req, "nota")));
        feedback.setComentario(this.param(req, "comentario"));
        return feedback;
    }

    /** Converte o texto da nota para numero inteiro. Se vazio/invalido, retorna null. */
    private Integer parseInt(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
