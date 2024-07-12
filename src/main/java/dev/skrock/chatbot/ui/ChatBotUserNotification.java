package dev.skrock.chatbot.ui;

import java.io.InputStream;
import java.util.Optional;

public interface ChatBotUserNotification {
    Optional<String> getMessage();

    Optional<InputStream> getSound();
}
