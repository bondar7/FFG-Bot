package com.bot.unrealfacegeneratorbot.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(
            DefaultBotOptions botOptions, String botToken
    ) {
        super(botOptions, botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chatId = update.getMessage().getChatId();
            var text = update.getMessage().getText();
        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }
}
