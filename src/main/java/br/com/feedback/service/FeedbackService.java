package br.com.feedback.service;

import br.com.feedback.dao.FeedbackDAO;
import br.com.feedback.dao.ProdutoDAO;
import br.com.feedback.dao.UsuarioDAO;
import br.com.feedback.model.Feedback;

import java.util.List;

/**
 * SERVICE de Feedback — regras de negocio.
 *
 * Alem do FeedbackDAO, usa ProdutoDAO e UsuarioDAO para validar que o produto
 * e o usuario escolhidos realmente existem antes de salvar.
 */
public class FeedbackService {

    private static final int NOTA_MINIMA = 1;
    private static final int NOTA_MAXIMA = 5;

    private final FeedbackDAO feedbackDAO;
    private final ProdutoDAO produtoDAO;
    private final UsuarioDAO usuarioDAO;

    public FeedbackService() {
        this.feedbackDAO = new FeedbackDAO();
        this.produtoDAO = new ProdutoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Feedback> listar() {
        return this.feedbackDAO.listarTodos();
    }

    public Feedback buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.feedbackDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id -> cadastro novo
     * - com id -> alteracao (feedback precisa existir)
     */
    public void salvar(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback e obrigatorio.");
        }

        feedback.setComentario(this.normalizar(feedback.getComentario()));

        this.validar(feedback);

        if (feedback.getId() == null) {
            this.feedbackDAO.inserir(feedback);
            return;
        }

        if (this.feedbackDAO.buscarPorId(feedback.getId()) == null) {
            throw new IllegalArgumentException("Feedback nao encontrado para alteracao.");
        }
        this.feedbackDAO.alterar(feedback);
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - feedback precisa existir
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.feedbackDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Feedback nao encontrado.");
        }
        this.feedbackDAO.deletar(id);
    }

    /** Valida produto, usuario e nota. */
    private void validar(Feedback feedback) {
        if (feedback.getProdutoId() == null) {
            throw new IllegalArgumentException("Selecione um produto.");
        }
        if (this.produtoDAO.buscarPorId(feedback.getProdutoId()) == null) {
            throw new IllegalArgumentException("Produto informado nao existe.");
        }

        if (feedback.getUsuarioId() == null) {
            throw new IllegalArgumentException("Selecione um usuario.");
        }
        if (this.usuarioDAO.buscarPorId(feedback.getUsuarioId()) == null) {
            throw new IllegalArgumentException("Usuario informado nao existe.");
        }

        if (feedback.getNota() == null) {
            throw new IllegalArgumentException("Nota e obrigatoria.");
        }
        if (feedback.getNota() < NOTA_MINIMA || feedback.getNota() > NOTA_MAXIMA) {
            throw new IllegalArgumentException("Nota deve ser de " + NOTA_MINIMA + " a " + NOTA_MAXIMA + ".");
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
