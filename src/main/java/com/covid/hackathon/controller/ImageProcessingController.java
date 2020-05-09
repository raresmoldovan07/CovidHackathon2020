package com.covid.hackathon.controller;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/image-processing")
public class ImageProcessingController {

    private ImageProcessingService imageProcessingService;

    @Autowired
    public ImageProcessingController(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @PostMapping("/")
    public Image processImage(@RequestBody Image image) {
        return imageProcessingService.processImage(image);
    }
}
