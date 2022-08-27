package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface CommandExecutor<M extends Message> {
    default Publisher<M> execute(Command<M> command, M message) {
        if (command instanceof ActionCommand<M>) {
            executeActionCommand((ActionCommand<M>) command, message);
        }
        if (command instanceof SoundCommand<M>) {
            executeSoundCommand((SoundCommand<M>) command, message);
        }
        if (command instanceof ResponseCommand<M>) {
            return executeResponseCommand((ResponseCommand<M>) command, message);
        }
        return Mono.empty();
    }

    void executeActionCommand(ActionCommand<M> command, M message);
    void executeSoundCommand(SoundCommand<M> command, M message);
    Publisher<M> executeResponseCommand(ResponseCommand<M> command, M message);
}
