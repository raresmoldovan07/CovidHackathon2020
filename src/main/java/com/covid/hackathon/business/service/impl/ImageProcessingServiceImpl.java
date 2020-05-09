package com.covid.hackathon.business.service.impl;

import com.covid.hackathon.business.service.ImageProcessingService;
import com.covid.hackathon.business.util.Converter;
import com.covid.hackathon.model.Image;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessingServiceImpl.class);

    private static final String PYTHON_COMMAND = "python";
    private static final String SCRIPT_PATH = "./UpperCase.py";
    private static final String TEXT = "text";

    private Base64 base64;
    private Converter converter;

    public ImageProcessingServiceImpl(Converter converter) {
        this.converter = converter;
        base64 = new Base64();
    }

    @Override
    public Image processImage(Image image) throws IOException {
        LOGGER.info("Processing new image");
//        String fetching = String.format("%s %s %s", PYTHON_COMMAND, SCRIPT_PATH, TEXT);
//        try {
//            Runtime.getRuntime().exec(fetching);
//        } catch (IOException e) {
//            LOGGER.error("Failed to run python script", e);
//            return null;
//        }
//        return new Image("text")
        BufferedImage bufferedImage = converter.convertBase64ToBufferedImage(image.getBase64Code());
        File outputfile = new File("image.png");
        ImageIO.write(bufferedImage, "png", outputfile);

        bufferedImage = ImageIO.read(new File("image.png"));
        String base64String = converter.convertBufferedImageToBase64(bufferedImage);
        image.setBase64Code(base64String);
        return image;
    }
}
