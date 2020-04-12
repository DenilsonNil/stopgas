package br.com.kualit.stopgas.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrinho {

    public static List<Produto> listaDeProdutos = new ArrayList<>();


    public static Double getValorTotal() {
        Double valorTotal = 0.0d;

        Iterator<Produto> iterator = listaDeProdutos.iterator();
        while (iterator.hasNext()) {
            valorTotal += iterator.next().getPreco();
        }

        return valorTotal;
    }


}
