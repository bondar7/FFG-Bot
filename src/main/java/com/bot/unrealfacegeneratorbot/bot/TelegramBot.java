package com.bot.unrealfacegeneratorbot.bot;

import com.bot.unrealfacegeneratorbot.image.ImageClient;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;

@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private String botToken;
    private String botName;
    private ImageClient imageClient;


    @Override
    public void onUpdateReceived(Update update) {
        // Checking if message is not empty
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();
            var text = update.getMessage().getText();
            switch (text) {
                case "/photo": sendPhoto(chatId); break;
                case "/start": start(chatId); break;
                default: unknownText(chatId); break;
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void start(Long chatId) {
        String startMessage = "Hello! This bot generates non-existent faces. Use /photo to generate one.";
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId.toString());
            sendMessage.setText(startMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void unknownText(Long chatId) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId.toString());
            sendMessage.setText("Please use specified commands!");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
}
