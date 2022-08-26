package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;

public interface ActionCommand<M extends Message> extends Command<M> {

    void execute(M message);
}
