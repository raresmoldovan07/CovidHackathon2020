package com.covid.hackathon.model;

public class FormattedImage {
    private String path;

    public FormattedImage(){

    }

    public FormattedImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
