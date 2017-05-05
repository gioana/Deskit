package com.deskit.model;

public class Faculty implements ListItem {

    private int id;

    private String name;

    private String code;

    private University university;

    public Faculty(int id, String name, University university) {
        this.id = id;
        this.name = name;
        this.university = university;
    }

    public Faculty(int id, String name) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return String.format("%s, %s", university.getName(), university.getCity().getName());
    }
}
