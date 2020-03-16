package br.com.kualit.stopgas;

import java.util.ArrayList;
import java.util.List;
public class Carrinho {

    private List<Produto> listaDeProdutos;


    public Carrinho(List<Produto> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    public List<Produto> getListaDeProdutos() {
        return listaDeProdutos;
    }

    public void setListaDeProdutos(List<Produto> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    public int getQuantidade(){
        return listaDeProdutos.size();
    }
    
    public Double getValorTotal(){
        Double valorTotal = 0.0;



        
        return null;
    }


}
