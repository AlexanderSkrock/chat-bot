package dev.skrock.chatbot.messaging;

public interface MessageOutConverter<Raw, M extends Message> {

    Raw convert(M message);
}
