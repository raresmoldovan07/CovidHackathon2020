package com.covid.hackathon.controller;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/image-processing")
@RequiredArgsConstructor
public class ImageProcessingController {

    private final ImageProcessingService imageProcessingService;

    /**
     * Returns received photo having a green square on the faces with mask and a red square for the ones without.
     *
     * @param image - base64 format image
     * @return - received photo after processing with an artificial intelligence algorithm from a python server.
     * @throws IOException - failed to save/load image
     */
    @PostMapping("/")
    public Image processImage(@RequestBody Image image) throws IOException {
        return imageProcessingService.processImage(image);
    }
}
