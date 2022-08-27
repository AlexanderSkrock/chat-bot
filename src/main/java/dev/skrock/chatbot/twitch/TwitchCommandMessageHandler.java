package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import dev.skrock.chatbot.twitch.messaging.irc.TwitchMessageHandler;
import dev.skrock.chatbot.twitch.messaging.irc.*;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TwitchCommandMessageHandler implements TwitchMessageHandler {

    private final TwitchCommandExecutor commandExecutor;
    private final List<TwitchChatCommand> commands;

    @Autowired
    public TwitchCommandMessageHandler(TwitchCommandExecutor commandExecutor, List<TwitchChatCommand> commands) {
        this.commandExecutor = commandExecutor;
        this.commands = commands;
    }

    @Override
    public void handle(TwitchMessage message, TwitchIRCClient ircClient) {
        if (!(message instanceof PrivMsgMessage) && message.getCommand() != TwitchCommands.PRIVMSG) {
            return;
        }

        PrivMsgMessage privMsgMessage = PrivMsgMessage.ofGenericMessage(message);

        Iterable<Publisher<PrivMsgMessage>> responsePublishers = commands
                .stream()
                .filter(command -> command.matches(privMsgMessage))
                .map(command -> commandExecutor.execute(command, privMsgMessage))
                .collect(Collectors.toList());

        Flux.mergeSequential(responsePublishers)
                .doOnNext(ircClient::sendMessage)
                .doOnError(error -> log.error("Fehler beim Auswerten der Nachricht: ", error));
    }
}
