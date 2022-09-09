package dev.skrock.chatbot.messaging;

import reactor.core.publisher.Flux;

public interface Connection<Raw> {
    boolean isConnected();
    void connect();
    void disconnect();

    Flux<Raw> getMessagesFlux();

    void sendMessage(Raw raw);
}
