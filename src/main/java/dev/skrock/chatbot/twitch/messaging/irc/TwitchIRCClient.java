package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.messaging.irc.IRCClient;
import dev.skrock.chatbot.twitch.messaging.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class TwitchIRCClient extends IRCClient<TwitchIRCConnection, TwitchMessage> {

    private final String nickname;
    private final String oauth;

    private boolean isAuthorized;

    private String channel;

    private final List<TwitchMessageHandler> additionalHandlers;

    public TwitchIRCClient(String nickname, String oauth, List<TwitchMessageHandler> messageHandlers) {
        super(new TwitchIRCConnection(), new TwitchMessageConverter(), new TwitchMessageConverter());
        this.nickname = nickname;
        this.oauth = oauth;
        this.additionalHandlers = new LinkedList<>(messageHandlers);
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized() {
        this.isAuthorized = true;
        log.info("Authenticated to the server");
    }

    @Override
    public void authorize() {
        // CAP REQ: https://dev.twitch.tv/docs/irc/capabilities
        sendMessage(new OAuthPassMessage(this.oauth));
        sendMessage(new NickMessage(this.nickname));
    }

    public void joinChannel() {
        sendMessage(new JoinMessage(channel));
        log.info("Joined to #" + this.channel);
    }

    public void joinChannel(String channel) {
        this.channel = channel;
        joinChannel();
    }

    public void sendPrivMessage(String channel, String message) {
        sendMessage(new PrivMsgMessage(channel, message));
    }

    @Override
    public void handleMessage(TwitchMessage message) {
        getDefaultHandlers().forEach(defaultHandler -> defaultHandler.handle(message, this));
        additionalHandlers.forEach(additionalHandler -> additionalHandler.handle(message, this));
    }

    private List<TwitchMessageHandler> getDefaultHandlers() {
        return List.of(
                new TwitchAuthorizationHandler(),
                new TwitchKeepAliveHandler()
        );
    }
}
