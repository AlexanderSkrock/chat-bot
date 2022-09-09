package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import reactor.core.publisher.Mono;

public interface CommandExecutor<C extends CommandContext, In extends Message, Out extends Message> {
    C provideContextForMessage(In message);

    default Mono<Out> execute(Command<C, In, Out> command, In message) {
        return command.execute(message, provideContextForMessage(message));
    }
}
