package com.bot.unrealfacegeneratorbot.image.config;

import com.bot.unrealfacegeneratorbot.image.ImageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageConfig {
    @Bean
    public ImageClient imageClient(
            @Value("image.url") String baseUrl
    ) {
        return new ImageClient(baseUrl);
    }
}
