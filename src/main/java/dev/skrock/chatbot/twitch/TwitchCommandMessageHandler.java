package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import dev.skrock.chatbot.twitch.messaging.irc.TwitchMessageHandler;
import dev.skrock.chatbot.twitch.messaging.irc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        commands.stream()
                .filter(command -> command.matches(privMsgMessage))
                .flatMap(command -> commandExecutor.execute(command, privMsgMessage).stream())
                .forEach(ircClient::sendMessage);
    }
}
