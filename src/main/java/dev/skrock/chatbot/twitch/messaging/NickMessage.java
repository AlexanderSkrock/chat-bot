package dev.skrock.chatbot.twitch.messaging;

public class NickMessage extends TwitchMessage {
    public NickMessage(String username) {
        super(new Tags(), new Source(), TwitchCommands.NICK, TwitchIRCCommandParameters.of(username), null);
    }
}
