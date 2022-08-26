package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;

import java.util.Optional;

public interface CommandExecutor<M extends Message> {
    default Optional<M> execute(Command<M> command, M message) {
        if (command instanceof ActionCommand<M>) {
            executeActionCommand((ActionCommand<M>) command, message);
        }
        if (command instanceof SoundCommand<M>) {
            executeSoundCommand((SoundCommand<M>) command, message);
        }
        if (command instanceof ResponseCommand<M>) {
            M responseMessage = executeResponseCommand((ResponseCommand<M>) command, message);
            return Optional.ofNullable(responseMessage);
        }
        return Optional.empty();
    }

    void executeActionCommand(ActionCommand<M> command, M message);
    void executeSoundCommand(SoundCommand<M> command, M message);
    M executeResponseCommand(ResponseCommand<M> command, M message);
}
