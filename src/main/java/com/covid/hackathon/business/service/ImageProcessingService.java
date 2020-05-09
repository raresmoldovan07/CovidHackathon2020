package com.covid.hackathon.business.service;

import com.covid.hackathon.model.Image;

import java.io.IOException;

public interface ImageProcessingService {

    Image processImage(Image image) throws IOException;
}
