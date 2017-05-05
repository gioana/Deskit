package com.deskit.model;

public class Subject implements ListItem {

    private String id;

    private String name;

    private Faculty faculty;

    public Subject(String id, String name, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return faculty.getName();
    }
}
