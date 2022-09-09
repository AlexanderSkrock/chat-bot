package dev.skrock.chatbot;

import dev.skrock.chatbot.core.ExtendableCrossPlatformChatBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatBotApplication.class, args);
	}

	@Autowired
	public void startChatBot(ExtendableCrossPlatformChatBot crossPlatformChatBot) {
		crossPlatformChatBot.initialize();
		crossPlatformChatBot.start();
	}
}
