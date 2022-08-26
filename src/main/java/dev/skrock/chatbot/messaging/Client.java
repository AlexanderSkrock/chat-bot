package dev.skrock.chatbot.messaging;

public interface Client<M extends Message> {
    void init();

    boolean isAuthorized();
    void authorize();

    void handleMessage(M message);
    void sendMessage(M message);
}
