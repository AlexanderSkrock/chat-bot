package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.twitch.messaging.irc.TwitchIRCClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class TwitchChatBot {

    private final TwitchIRCClient twitchClient;

    private final String channel;

    @Autowired
    public TwitchChatBot(TwitchChatBotConfiguration configuration, TwitchCommandMessageHandler commandMessageHandler) {
        this.twitchClient = new TwitchIRCClient(configuration.getUsername(), configuration.getToken(), Collections.singletonList(commandMessageHandler));
        this.channel = configuration.getChannel();
    }

    @PostConstruct
    public void init() {
        twitchClient.init();
        twitchClient.joinChannel(channel);
    }
}
