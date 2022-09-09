package dev.skrock.chatbot.core;

import java.util.Optional;

public interface CrossPlatformChatBot extends ChatBot, IsCrossPlatform {

    Optional<ChatBot> getChatBotForPlatform(Platform platform);
}
