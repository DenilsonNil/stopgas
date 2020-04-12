package br.com.kualit.stopgas.model;

import java.io.Serializable;

public class MailPass implements Serializable {

    private int id;
    private String mail;
    private String pass;

    public MailPass(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
