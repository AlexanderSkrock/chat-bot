package dev.skrock.chatbot.twitch.messaging;

public class PassMessage extends TwitchMessage {
    public PassMessage(String token) {
        super(new Tags(), new Source(), TwitchCommands.PASS, TwitchIRCCommandParameters.of(token), null);
    }
}
