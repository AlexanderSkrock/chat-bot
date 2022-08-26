package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;

public interface Command<M extends Message> {

    boolean matches(M message);
}
