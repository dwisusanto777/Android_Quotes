package com.example.course2kitaquotes.model;

public class KitaQuotes {
    private int id;
    private String name;
    public KitaQuotes() {
    }
    public KitaQuotes(int id, String name) {
        this.id = id;
        this.name = name;
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
}
