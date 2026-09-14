package br.com.feedback.service;

import br.com.feedback.dao.ProdutoDAO;
import br.com.feedback.model.Produto;

import java.math.BigDecimal;
import java.util.List;

/**
 * SERVICE de Produto — as REGRAS DE NEGOCIO ficam aqui.
 *
 * O Controller so chama estes metodos e escolhe a tela.
 * O DAO so executa SQL. O Service fica no meio, validando.
 */
public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public List<Produto> listar() {
        return this.produtoDAO.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return this.produtoDAO.buscarPorId(id);
    }

    /**
     * Regra de salvamento:
     * - sem id  -> cadastro novo (inserir)
     * - com id  -> alteracao (produto precisa existir)
     */
    public void salvar(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto e obrigatorio.");
        }

        produto.setNome(this.normalizar(produto.getNome()));
        produto.setDescricao(this.normalizar(produto.getDescricao()));

        this.validar(produto);

        if (produto.getId() == null) {
            this.produtoDAO.inserir(produto);
            return;
        }

        if (this.produtoDAO.buscarPorId(produto.getId()) == null) {
            throw new IllegalArgumentException("Produto nao encontrado para alteracao.");
        }
        this.produtoDAO.alterar(produto);
    }

    /**
     * Regra de exclusao:
     * - id obrigatorio
     * - produto precisa existir
     */
    public void deletar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id e obrigatorio para excluir.");
        }
        if (this.produtoDAO.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Produto nao encontrado.");
        }
        this.produtoDAO.deletar(id);
    }

    /** Valida os campos obrigatorios do produto. */
    private void validar(Produto produto) {
        if (produto.getNome() == null) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }
        if (produto.getDescricao() == null) {
            throw new IllegalArgumentException("Descricao e obrigatoria.");
        }
        if (produto.getPreco() == null) {
            throw new IllegalArgumentException("Preco e obrigatorio (use numeros, ex: 199.90).");
        }
        if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preco nao pode ser negativo.");
        }
    }

    /** Tira espacos das pontas; string vazia vira null. */
    private String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
