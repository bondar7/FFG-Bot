package com.bot.unrealfacegeneratorbot.image;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@AllArgsConstructor
public class ImageClient {

    private final String BASE_URL;

    // Method to fetch the image and return an InputStream
    public InputStream fetchImage() {
        int originalImageHeight = 1024;
        int croppedImageHeight = 1000;
        try {
            // Creating httpclient
            HttpClient httpClient = HttpClients.createDefault();
            // Creating request that requires only url
            HttpGet request = new HttpGet(BASE_URL);
            // Getting response
            HttpResponse response = httpClient.execute(request);
            // Check if response is an image
            String contentType = response.getEntity().getContentType().getValue();
            if (contentType.contains("image")) {
                // Crop image's height before sending
                InputStream inputStream = response.getEntity().getContent();
                BufferedImage originalImage = ImageIO.read(inputStream);
                BufferedImage croppedImage = originalImage.getSubimage(0, 0, originalImage.getWidth(), croppedImageHeight);

                // Convert the cropped image back to InputStream
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(croppedImage, "jpg", baos);
                return new ByteArrayInputStream(baos.toByteArray()); // Return the InputStream without closing it
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}