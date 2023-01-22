package dev.skrock.chatbot.ui;

import java.io.InputStream;
import java.util.Optional;

public class SimpleChatBotUserNotification implements ChatBotUserNotification {

    private final String message;
    private final InputStream sound;

    public SimpleChatBotUserNotification(String message) {
        this(message, null);
    }

    public SimpleChatBotUserNotification(InputStream sound) {
        this(null, sound);
    }

    public SimpleChatBotUserNotification(String message, InputStream sound) {
        this.message = message;
        this.sound = sound;
    }

    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<InputStream> getSound() {
        return Optional.ofNullable(sound);
    }
}
