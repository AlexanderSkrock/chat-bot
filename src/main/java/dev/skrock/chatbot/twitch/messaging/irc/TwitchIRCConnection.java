package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.messaging.irc.IRCConnection;

public class TwitchIRCConnection extends IRCConnection {

    public static final String TWITCH_IRC_URL = "irc.chat.twitch.tv";
    public static final int TWITCH_IRC_PORT = 6667;

    public TwitchIRCConnection() {
        super(TWITCH_IRC_URL, TWITCH_IRC_PORT);
    }
}
