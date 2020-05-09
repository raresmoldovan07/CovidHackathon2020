package com.covid.hackathon.business.service.impl;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.business.util.Converter;
import com.covid.hackathon.model.FormattedImage;
import com.covid.hackathon.model.Image;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessingServiceImpl.class);

    private static final String IMAGE_EXTENSION = "png";
    private static final String IMAGE_PATH = "imagePath";
    private static final String PYTHON_SERVER_URI = "http://localhost:8082/processImage";

    private Base64 base64;
    private RestTemplate restTemplate;
    private Converter converter;

    @Autowired
    public ImageProcessingServiceImpl(Converter converter, Base64 base64, RestTemplate restTemplate) {
        this.converter = converter;
        this.base64 = base64;
        this.restTemplate = restTemplate;
    }

    @Override
    public Image processImage(Image image) throws IOException {
        LOGGER.info("Processing new image");
        String imagePath = saveImage(image);
        String processedImageFileName = requestImageProcessing(imagePath);
        return loadProcessedImage(processedImageFileName);
    }

    private String saveImage(Image image) throws IOException {
        String imageFileName = String.format("%s.%s", UUID.randomUUID(), IMAGE_EXTENSION);
        LOGGER.info("Saving new image in {}", imageFileName);
        File outputfile = new File(imageFileName);
        BufferedImage bufferedImage = converter.convertBase64ToBufferedImage(image.getBase64Code());
        try {
            ImageIO.write(bufferedImage, IMAGE_EXTENSION, outputfile);
            return imageFileName;
        } catch (IOException e) {
            LOGGER.error("Failed to save image", e);
            throw e;
        }
    }

    private Image loadProcessedImage(String imageFileName) throws IOException {
        LOGGER.info("Loading processed image from {}", imageFileName);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(imageFileName));
        } catch (IOException e) {
            LOGGER.error("Failed to load processed image", e);
            throw e;
        }
        String base64String = converter.convertBufferedImageToBase64(bufferedImage);
        return new Image(base64String);
    }

    private String requestImageProcessing(String imagePath){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject path = new JSONObject();
        path.put(IMAGE_PATH, imagePath);
        HttpEntity<String> request =new HttpEntity<>(path.toString(), headers);
        FormattedImage formattedImage = restTemplate.postForObject(PYTHON_SERVER_URI, request, FormattedImage.class);
        if (formattedImage == null) {
            throw new RuntimeException("Failed to receive formatted image");
        }
        return formattedImage.getImagePath();
    }
}
