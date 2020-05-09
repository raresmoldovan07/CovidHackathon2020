package com.covid.hackathon.business.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class Converter {

    private Base64 base64 = new Base64();

    public BufferedImage convertBase64ToBufferedImage(String base64String) {
        byte[] imageByte = base64.decode(base64String);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte)) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertBufferedImageToBase64(BufferedImage bufferedImage) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            outputStream.flush();
            byte[] imageByte = outputStream.toByteArray();
            return new String(base64.encode(imageByte));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
