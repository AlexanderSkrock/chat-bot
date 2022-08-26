package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.twitch.messaging.PingMessage;
import dev.skrock.chatbot.twitch.messaging.PongMessage;
import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitchKeepAliveHandler implements TwitchMessageHandler {

    @Override
    public void handle(TwitchMessage message, TwitchIRCClient twitchIRCClient) {
        if (message.getCommand() == TwitchCommands.PING) {
            log.info("Responding to PING: . . . ");

            PingMessage ping = PingMessage.ofGenericMessage(message);
            PongMessage pong = PongMessage.forPing(ping);

            twitchIRCClient.sendMessage(pong);
        }
    }
}
