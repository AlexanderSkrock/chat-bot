package dev.skrock.chatbot.twitch.messaging;

public class PingMessage extends TwitchMessage {
    public static PingMessage ofGenericMessage(TwitchMessage message) {
        if (message instanceof PingMessage) {
            return (PingMessage) message;
        } else if (message.getCommand() == TwitchCommands.PING) {
            return new PingMessage(message.getTags(), message.getSource(), message.getParameters(), message.getMessage());
        }
        throw new IllegalArgumentException("cant convert input message: " + message);
    }

    public PingMessage(Tags tags, Source source, TwitchIRCCommandParameters parameters, String message) {
        super(tags, source, TwitchCommands.PING, parameters, message);
    }
}
