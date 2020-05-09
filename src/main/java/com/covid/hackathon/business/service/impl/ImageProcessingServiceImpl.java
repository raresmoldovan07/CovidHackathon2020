package com.covid.hackathon.business.service.impl;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.business.util.Converter;
import com.covid.hackathon.model.FormattedImage;
import com.covid.hackathon.model.Image;
import com.fasterxml.jackson.databind.JsonNode;
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

    private Base64 base64;
    private RestTemplate restTemplate;
    private Converter converter;

    @Autowired
    public ImageProcessingServiceImpl(Converter converter, Base64 base64, RestTemplate restTemplate) {
        this.converter = converter;
        this.base64 = base64;
        this.restTemplate = restTemplate;
    }

    private String requestImageFormat(String imagePath){
        String uri = "http://localhost:8082/processImage";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject path = new JSONObject();
        path.put("imagePath", imagePath);
        HttpEntity<String> request =new HttpEntity<String>(path.toString(), headers);
        FormattedImage formattedImage = restTemplate.postForObject(uri, request, FormattedImage.class);
        return formattedImage.getImagePath();
    }

    @Override
    public Image processImage(Image image) throws IOException {
        LOGGER.info("Processing new image");
        String imageFileName = String.format("%s.%s", UUID.randomUUID(), IMAGE_EXTENSION);
        File outputfile = new File(imageFileName);
        BufferedImage bufferedImage = converter.convertBase64ToBufferedImage(image.getBase64Code());
        ImageIO.write(bufferedImage, IMAGE_EXTENSION, outputfile);

        String modifiedImagePath = requestImageFormat(imageFileName);

        bufferedImage = ImageIO.read(new File(modifiedImagePath));
        String base64String = converter.convertBufferedImageToBase64(bufferedImage);
        image.setBase64Code(base64String);
        return image;
    }
}
