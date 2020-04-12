package br.com.kualit.stopgas.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int id;
    private String identificador;
    private String userMail;
    private String address;
    private String data;
    private String tipoPagamento;
    private List<Produto> produtos;

    public Pedido() {
    }



    public Pedido(int id, String identificador, String userMail, String address, String data, String tipoPagamento) {
        this.identificador = identificador;
        this.userMail = userMail;
        this.address = address;
        this.data = data;
        this.tipoPagamento = tipoPagamento;
        this.id = id;
    }

    public Pedido(String identificador, String userMail, String address, String data, String tipoPagamento) {
        this.identificador = identificador;
        this.userMail = userMail;
        this.address = address;
        this.data = data;
        this.tipoPagamento = tipoPagamento;
        this.produtos = produtos;
        this.id = id;
    }

    public Pedido(String userMail, String address, String data, String tipoPagamento, List<Produto> produtos) {
        this.identificador = identificador;
        this.userMail = userMail;
        this.address = address;
        this.data = data;
        this.tipoPagamento = tipoPagamento;
        this.produtos = produtos;
    }



    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos.addAll( produtos );
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
