package com.deskit.model;

public class University implements ListItem {

    private int id;

    private String name;

    private City city;

    public University(int id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public University(int id, String name) {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return city.getName();
    }
}
