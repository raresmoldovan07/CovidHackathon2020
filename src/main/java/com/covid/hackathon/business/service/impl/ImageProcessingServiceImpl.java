package com.covid.hackathon.business.service.impl;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessingServiceImpl.class);

    private static final String PYTHON_COMMAND = "python";
    private static final String SCRIPT_PATH = "./UpperCase.py";
    private static final String TEXT = "text";

    @Override
    public Image processImage(Image image) {
        LOGGER.info("Processing new image");
        String fetching = String.format("%s %s %s", PYTHON_COMMAND, SCRIPT_PATH, TEXT);
        try {
            Runtime.getRuntime().exec(fetching);
        } catch (IOException e) {
            LOGGER.error("Failed to run python script", e);
            return null;
        }
        return new Image("text");
    }
}
