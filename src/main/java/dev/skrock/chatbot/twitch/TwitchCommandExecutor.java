package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.messaging.Message;
import dev.skrock.chatbot.twitch.command.TwitchCommandContext;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.variables.TwitchMessageVariableReplacer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
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
    public Publisher<PrivMsgMessage> execute(Command<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage> command, PrivMsgMessage message) {
        return Mono.from(CommandExecutor.super.execute(command, message))
                .map(response -> {
            response.setMessage(variableReplacer.replace(response.getMessage(), message));
            return response;
        });
    }
}
