package com.ssafy.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.ssafy.trip.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class chatbot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		System.out.println(update);
		System.out.println(update.getMessage().getChatId());
		System.out.println(update.getMessage().getText());
		System.out.println(update.getMessage().getDocument().getFileId());
		System.out.println(update.getMessage().getDate());
		System.out.println(update.getMessage().getPhoto());
		System.out.println(update.getMessage().getVideo());

	}

	@Override
	public String getBotUsername() {
		return "post_temp_bot";
	}

	@Override
	public String getBotToken() {
		return "1242742917:AAEtosqpo7-uGbzEHqAsY-CwRyZAXqYyViA";
	}
}