package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import reactor.core.publisher.Mono;

public interface Command<C extends CommandContext, In extends Message, Out extends Message> {

    boolean matches(In message);

    Mono<Out> execute(In message, C context);
}
