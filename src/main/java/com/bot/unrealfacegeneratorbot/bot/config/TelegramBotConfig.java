package com.bot.unrealfacegeneratorbot.bot.config;

import com.bot.unrealfacegeneratorbot.bot.TelegramBot;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

        @Bean
        @SneakyThrows
        public TelegramBot telegramBot(
            @Value("${bot.token}") String botToken
    ) {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        TelegramBot bot = new TelegramBot(botOptions, botToken);
        telegramBotsApi().registerBot(bot);
        return bot;
    }

    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi(DefaultBotSession.class);
    }
}
