package com.covid.hackathon.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {

    private String text;

    public Image() {
    }

    public Image(String text) {
        this.text = text;
    }
}
