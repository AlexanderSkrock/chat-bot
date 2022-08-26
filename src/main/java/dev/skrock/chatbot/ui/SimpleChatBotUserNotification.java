package dev.skrock.chatbot.ui;

import dev.skrock.chatbot.storage.Sound;
import lombok.Data;

import java.util.Optional;

public class SimpleChatBotUserNotification implements ChatBotUserNotification {

    private final String message;
    private final Sound sound;

    public SimpleChatBotUserNotification(String message) {
        this(message, null);
    }

    public SimpleChatBotUserNotification(Sound sound) {
        this(null, sound);
    }

    public SimpleChatBotUserNotification(String message, Sound sound) {
        this.message = message;
        this.sound = sound;
    }

    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    @Override
    public Optional<Sound> getSound() {
        return Optional.ofNullable(sound);
    }
}
