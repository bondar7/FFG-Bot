package com.bot.unrealfacegeneratorbot.image;

import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.FileOutputStream;
import java.io.InputStream;

@AllArgsConstructor
public class ImageClient {

    private final String BASE_URL;

    // Method to fetch the image and return an InputStream
    public InputStream fetchImage() {
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
                return response.getEntity().getContent(); // Return the InputStream without closing it
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}