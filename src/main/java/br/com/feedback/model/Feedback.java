package br.com.feedback.model;

/**
 * MODEL Feedback — espelha a tabela "feedback".
 *
 * E a entidade que LIGA um usuario a um produto, com uma nota e um comentario.
 * Guardamos os ids das chaves estrangeiras (usuarioId, produtoId) E os objetos
 * completos (usuario, produto), para conseguir exibir os nomes nas telas.
 */
public class Feedback {

    private Long id;
    private Long usuarioId;
    private Long produtoId;
    private Integer nota;
    private String comentario;

    private Usuario usuario;
    private Produto produto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
