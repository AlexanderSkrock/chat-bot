package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import org.reactivestreams.Publisher;

public interface Command<C extends CommandContext, In extends Message, Out extends Message> {

    boolean matches(In message);

    Publisher<Out> execute(In message, C context);
}
