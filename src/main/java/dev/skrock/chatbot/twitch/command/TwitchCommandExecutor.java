package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.variables.TwitchMessageVariableReplacer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TwitchCommandExecutor implements CommandExecutor<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage> {
    private final ChatBotUserInterface userInterface;
    private final TwitchMessageVariableReplacer variableReplacer;

    public TwitchCommandExecutor(ChatBotUserInterface userInterface, TwitchMessageVariableReplacer variableReplacer) {
        this.userInterface = userInterface;
        this.variableReplacer = variableReplacer;
    }

    @Override
    public TwitchCommandContext provideContextForMessage(PrivMsgMessage message) {
        return new TwitchCommandContext(userInterface);
    }

    @Override
    public Mono<PrivMsgMessage> execute(Command<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage> command, PrivMsgMessage message) {
        log.debug("Executing command: {}", command);
        return CommandExecutor.super.execute(command, message).map(response -> {
            response.setMessage(variableReplacer.replace(response.getMessage(), message));
            return response;
        });
    }
}
