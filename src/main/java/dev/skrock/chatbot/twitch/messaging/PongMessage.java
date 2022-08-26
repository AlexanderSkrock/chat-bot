package dev.skrock.chatbot.twitch.messaging;

public class PongMessage extends TwitchMessage {
    public static PongMessage forPing(PingMessage message) {
        return new PongMessage(message.getTags(), message.getSource(), message.getParameters(), message.getMessage());
    }

    public PongMessage(Tags tags, Source source, TwitchIRCCommandParameters parameters, String message) {
        super(tags, source, TwitchCommands.PONG, parameters, message);
    }
}
