package dev.skrock.chatbot.messaging;

public interface MessageInConverter<Raw, M extends Message> {

    M convert(Raw raw);
}
