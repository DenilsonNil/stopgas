package br.com.kualit.stopgas;

public class Produto {
    private String descricao;
    private Double preco;
    private int imagem;

    public Produto(int imagem, String descricao, Double preco) {
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
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
