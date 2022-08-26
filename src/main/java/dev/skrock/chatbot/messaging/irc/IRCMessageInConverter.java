package dev.skrock.chatbot.messaging.irc;

import dev.skrock.chatbot.messaging.Message;
import dev.skrock.chatbot.messaging.MessageInConverter;

public interface IRCMessageInConverter<M extends Message> extends MessageInConverter<String, M> {

    M convert(String rawMessage);
}
