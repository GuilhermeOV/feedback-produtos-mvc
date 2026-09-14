package br.com.feedback.controller;

import br.com.feedback.model.Produto;
import br.com.feedback.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * CONTROLLER de Produto.
 * Ponte entre a rota (/produtos), o {@link ProdutoService} e as telas (JSP).
 * NAO tem regra de negocio — so organiza o fluxo.
 */
@WebServlet("/produtos")
public class ProdutoServlet extends BaseServlet {

    private static final String LISTA = "/WEB-INF/jsp/produtos/lista.jsp";
    private static final String FORM = "/WEB-INF/jsp/produtos/form.jsp";

    private final ProdutoService produtoService = new ProdutoService();

    /** Trata os cliques (links) — listar, abrir formulario, excluir. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        switch (this.acao(req)) {
            case "novo" -> this.form(req, resp, null);
            case "editar" -> this.form(req, resp, this.produtoService.buscarPorId(this.paramLong(req, "id")));
            case "excluir" -> {
                try {
                    this.produtoService.deletar(this.paramLong(req, "id"));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("erro", e.getMessage());
                    req.setAttribute("produtos", this.produtoService.listar());
                    this.forward(req, resp, LISTA);
                    return;
                }
                this.redirect(req, resp, "/produtos");
            }
            default -> {
                req.setAttribute("produtos", this.produtoService.listar());
                this.forward(req, resp, LISTA);
            }
        }
    }

    /** Trata o envio do formulario (botao Salvar) — cadastrar ou alterar. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        Produto produto = this.fromRequest(req);

        try {
            this.produtoService.salvar(produto);
            this.redirect(req, resp, "/produtos");
        } catch (IllegalArgumentException e) {
            req.setAttribute("erro", e.getMessage());
            this.form(req, resp, produto);
        }
    }

    /** Mostra o formulario (vazio para novo, preenchido para editar). */
    private void form(HttpServletRequest req, HttpServletResponse resp, Produto produto)
            throws ServletException, IOException {

        if ("editar".equals(this.acao(req)) && produto == null) {
            this.redirect(req, resp, "/produtos");
            return;
        }

        req.setAttribute("produto", produto);
        this.forward(req, resp, FORM);
    }

    /** Monta um objeto Produto com os dados digitados na tela. */
    private Produto fromRequest(HttpServletRequest req) {
        Produto produto = new Produto();
        produto.setId(this.paramLong(req, "id"));
        produto.setNome(this.param(req, "nome"));
        produto.setDescricao(this.param(req, "descricao"));
        produto.setPreco(this.parseBigDecimal(this.param(req, "preco")));
        return produto;
    }

    /** Converte o texto do preco para numero. Se estiver vazio/invalido, retorna null. */
    private BigDecimal parseBigDecimal(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        try {
            return new BigDecimal(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
