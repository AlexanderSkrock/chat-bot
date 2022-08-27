package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import org.reactivestreams.Publisher;

public interface ResponseCommand<M extends Message> extends Command<M> {

    Publisher<M> getResponse(M message);
}
