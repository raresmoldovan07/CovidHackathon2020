package com.covid.hackathon.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {

    private String base64Code;

    public Image() {
    }

    public Image(String base64Code) {
        this.base64Code = base64Code;
    }

    public String getBase64Code() {
        return base64Code;
    }

    public void setBase64Code(String base64Code) {
        this.base64Code = base64Code;
    }
}
