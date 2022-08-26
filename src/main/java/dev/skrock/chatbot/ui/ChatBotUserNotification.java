package dev.skrock.chatbot.ui;

import dev.skrock.chatbot.storage.Sound;

import java.util.Optional;

public interface ChatBotUserNotification {
    Optional<String> getMessage();

    Optional<Sound> getSound();
}
