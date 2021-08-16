package com.example.course2kitaquotes.model;

public class AuthorModel {

    private int authorId;
    private String authorName;
    private String authorImage;

    public AuthorModel(int authorId, String authorName, String authorImage) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.authorImage = authorImage;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }
}
