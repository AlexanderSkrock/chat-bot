package dev.skrock.chatbot;

import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.vaadin.VaadinUserInterfaceBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatBotConfiguration {

    @Bean
    public ChatBotUserInterface userInterface() {
        return new VaadinUserInterfaceBridge();
    }
}
