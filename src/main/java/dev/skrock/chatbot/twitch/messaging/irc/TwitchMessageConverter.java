package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.messaging.irc.IRCMessageInConverter;
import dev.skrock.chatbot.messaging.irc.IRCMessageOutConverter;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;

public class TwitchMessageConverter implements IRCMessageInConverter<TwitchMessage>, IRCMessageOutConverter<TwitchMessage> {
    @Override
    public TwitchMessage convert(String rawMessage) {
        return new TwitchMessageParser().parse(rawMessage);
    }

    @Override
    public String convert(TwitchMessage message) {
        return new TwitchMessageParser().format(message);
    }
}
