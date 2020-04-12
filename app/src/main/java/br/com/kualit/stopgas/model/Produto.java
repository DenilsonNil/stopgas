package br.com.kualit.stopgas.model;

public class Produto {
    private String descricao;
    private Double preco;
    private int imagem;
    private String id_pedido;


    public Produto(String id_pedido, String descricao){

        this.id_pedido = id_pedido;
        this.descricao = descricao;

    }


    public Produto(int imagem, String descricao, Double preco) {
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
    }


    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
