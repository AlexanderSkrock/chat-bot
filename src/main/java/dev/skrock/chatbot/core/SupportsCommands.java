package dev.skrock.chatbot.core;

import dev.skrock.chatbot.command.Command;

import java.util.Set;

public interface SupportsCommands<CommandType extends Command<?, ?, ?>> {

    boolean supportsCommands();
    void enableCommandSupport();
    void disableCommandSupport();

    void addCommand(CommandType command);
    Set<? extends CommandType> getCommands();
}
