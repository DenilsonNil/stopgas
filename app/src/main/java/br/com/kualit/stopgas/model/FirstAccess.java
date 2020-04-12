package br.com.kualit.stopgas.model;

public class FirstAccess {

    private int id;
    private int controller;

    public FirstAccess(int controller) {
        this.controller = controller;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getController() {
        return controller;
    }

    public void setController(int controller) {
        this.controller = controller;
    }
}
