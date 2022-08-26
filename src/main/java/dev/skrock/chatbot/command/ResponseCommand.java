package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;

public interface ResponseCommand<M extends Message> extends Command<M> {

    M getResponse(M message);
}
