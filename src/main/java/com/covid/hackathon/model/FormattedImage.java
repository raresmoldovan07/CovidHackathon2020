package com.covid.hackathon.model;

public class FormattedImage {
    private String imagePath;

    public FormattedImage(){

    }

    public FormattedImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
