package dev.skrock.chatbot.extensions.twitch;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.twitch.command.TwitchCommandContext;
import dev.skrock.chatbot.twitch.command.TwitchCommandExecutor;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import dev.skrock.chatbot.twitch.messaging.irc.TwitchMessageHandler;
import dev.skrock.chatbot.twitch.messaging.irc.*;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class TwitchCommandMessageHandler implements TwitchMessageHandler {

    private final TwitchCommandExecutor commandExecutor;
    private final Set<? extends Command<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage>> commands;

    @Autowired
    public TwitchCommandMessageHandler(TwitchCommandExecutor commandExecutor, Set<? extends Command<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage>> commands) {
        this.commandExecutor = commandExecutor;
        this.commands = commands;
    }

    @Override
    public void handle(TwitchMessage message, TwitchIRCClient ircClient) {
        if (!(message instanceof PrivMsgMessage) && message.getCommand() != TwitchCommands.PRIVMSG) {
            return;
        }

        PrivMsgMessage privMsgMessage = PrivMsgMessage.ofGenericMessage(message);

        commands.forEach(command -> {
           if (!command.matches(privMsgMessage)) {
               return;
           }
           commandExecutor.execute(command, privMsgMessage).doOnNext(response -> {
               log.debug("Sending response: {}", response);
               ircClient.sendMessage(response);
           }).subscribe();
        });
    }
}
