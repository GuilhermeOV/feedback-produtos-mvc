package br.com.feedback.model;

import java.math.BigDecimal;

/**
 * MODEL Produto — espelha a tabela "produtos".
 * Cada coluna do banco vira um atributo desta classe.
 * BigDecimal e o tipo correto para dinheiro (preco), evita erros de arredondamento.
 */
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
