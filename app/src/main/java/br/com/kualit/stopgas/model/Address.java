package br.com.kualit.stopgas.model;

import androidx.annotation.NonNull;

import br.com.kualit.stopgas.db.AddressDAO;

public class Address {

    private int id;
    private String user;
    private String name;
    private String address;
    private String tel;
    private String bairro;
    private String city;

    public Address(String user, String name, String address, String tel, String bairro, String city) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.bairro = bairro;
        this.city = city;
    }


    @NonNull
    @Override
    public String toString() {

        String fullAddress = getAddress() + "-" + getBairro();
        return fullAddress;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
