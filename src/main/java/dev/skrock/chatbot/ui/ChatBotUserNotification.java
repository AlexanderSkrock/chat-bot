package dev.skrock.chatbot.ui;

import dev.skrock.chatbot.audio.Sound;

import java.util.Optional;

public interface ChatBotUserNotification {
    Optional<String> getMessage();

    Optional<Sound> getSound();
}
