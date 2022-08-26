package dev.skrock.chatbot.twitch.messaging;

public class JoinMessage extends TwitchMessage {
    public JoinMessage(String channel) {
        super(new Tags(), new Source(), TwitchCommands.JOIN, TwitchIRCCommandParameters.of(TwitchMessageUtils.channelParameter(channel)), "");
    }
}
