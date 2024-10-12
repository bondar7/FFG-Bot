package com.bot.unrealfacegeneratorbot.bot;

import com.bot.unrealfacegeneratorbot.image.ImageClient;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TelegramBot extends TelegramLongPollingBot {

    private ImageClient imageClient;

    public TelegramBot(
            DefaultBotOptions botOptions, String botToken, ImageClient imageClient
    ) {
        super(botOptions, botToken);
        this.imageClient = imageClient;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        // Checking if message is not empty
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();
            var text = update.getMessage().getText();
            switch (text) {
                case "/photo": sendPhoto(chatId); break;
            }
            }
    }

    private void sendPhoto(Long chatId) {
        try {
            // Get input stream of image
            InputStream inputStream = imageClient.fetchImage();
            if (inputStream != null) {
               InputFile inputFile = new InputFile(inputStream, "unreal-face.jpg");
               String caption = "A new photo for you!";
                // Prepare photo before sending
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setCaption(caption);
                sendPhoto.setPhoto(inputFile);
                // Send photo
                execute(sendPhoto);
                // Close input stream
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "unreal-face-generator-bot";
    }
}
