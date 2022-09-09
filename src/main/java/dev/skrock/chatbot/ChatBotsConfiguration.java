package dev.skrock.chatbot;

import dev.skrock.chatbot.core.ChatBotExtension;
import dev.skrock.chatbot.core.ExtendableChatBot;
import dev.skrock.chatbot.core.ExtendableCrossPlatformChatBot;
import dev.skrock.chatbot.discord.DiscordConfiguration;
import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.twitch.TwitchChatBot;
import dev.skrock.chatbot.twitch.TwitchChatBotConfiguration;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.vaadin.VaadinUserInterfaceBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatBotsConfiguration {

    @Bean
    public ChatBotUserInterface userInterface() {
        return new VaadinUserInterfaceBridge();
    }

    @Bean
    public DiscordChatBot discordChatBot(DiscordConfiguration discordConfiguration, List<ChatBotExtension<DiscordChatBot>> extensions) {
        return new DiscordChatBot(discordConfiguration, extensions);
    }

    @Bean
    public TwitchChatBot twitchChatBot(TwitchChatBotConfiguration twitchConfiguration, List<ChatBotExtension<TwitchChatBot>> extensions) {
        return new TwitchChatBot(twitchConfiguration, extensions);
    }

    @Bean
    public ExtendableCrossPlatformChatBot crossPlatformChatBot(List<ExtendableChatBot> platformBots, List<ChatBotExtension<ExtendableCrossPlatformChatBot>> extensions) {
        return new ExtendableCrossPlatformChatBot(platformBots, extensions);
    }
}
