package dev.skrock.chatbot.twitch.messaging;

public class PrivMsgMessage extends TwitchMessage {
    public static PrivMsgMessage ofGenericMessage(TwitchMessage message) {
        if (message instanceof PrivMsgMessage) {
            return (PrivMsgMessage) message;
        } else if (message.getCommand() == TwitchCommands.PRIVMSG) {
            String channel = message.getParameters().isEmpty() ? null : message.getParameters().get(0);
            return new PrivMsgMessage(message.getTags(), message.getSource(), channel, message.getMessage());
        }
        throw new IllegalArgumentException("cant convert input message: " + message);
    }

    public PrivMsgMessage(String channel, String message) {
        this(new Tags(), new Source(), channel, message);
    }

    public PrivMsgMessage(Tags tags, Source source, String channel, String message) {
        super(tags, source, TwitchCommands.PRIVMSG, TwitchIRCCommandParameters.of(TwitchMessageUtils.channelParameter(channel)), message);
    }

    public String getChannel() {
        return getParameters().isEmpty() ? null : getParameters().get(0);
    }
}
