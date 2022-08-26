package dev.skrock.chatbot.messaging;

public interface MessageHandler<C extends Client<M>, M extends Message> {

    void handle(M message, C ircClient);
}
