package com.deskit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Resource implements ListItem {

    private String id;

    private String name;

    private String author;

    private String professor;

    private
    @FileType
    int fileType;

    private long dateInMilliseconds;

    private String details;

    private int size;

    private Subject subject;

    private List<Comment> commentList;

    public Resource(String id, String name, String author, String professor, int fileType, long dateInMilliseconds, String details, int size, Subject subject) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.professor = professor;
        this.fileType = fileType;
        this.dateInMilliseconds = dateInMilliseconds;
        this.details = details;
        this.size = size;
        this.subject = subject;
        this.commentList = new ArrayList<>();
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public long getDateInMilliseconds() {
        return dateInMilliseconds;
    }

    public void setDateInMilliseconds(long dateInMilliseconds) {
        this.dateInMilliseconds = dateInMilliseconds;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String getTitle() {
        return String.format(Locale.getDefault(),
                "%s (%s, %s, %d comments)", name, fileType, size, commentList.size());
    }

    @Override
    public String getSubTitle() {
        return null;
    }
}
