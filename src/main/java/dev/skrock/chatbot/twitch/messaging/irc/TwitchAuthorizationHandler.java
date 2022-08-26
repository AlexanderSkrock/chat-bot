package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitchAuthorizationHandler implements TwitchMessageHandler {

    @Override
    public void handle(TwitchMessage message, TwitchIRCClient ircClient) {
        if (message.getCommand() == TwitchCommands.SUCCESSFUL_LOGIN) {
            ircClient.setAuthorized();
        }
    }
}
