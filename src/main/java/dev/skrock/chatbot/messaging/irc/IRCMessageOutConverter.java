package dev.skrock.chatbot.messaging.irc;

import dev.skrock.chatbot.messaging.Message;
import dev.skrock.chatbot.messaging.MessageOutConverter;

public interface IRCMessageOutConverter<M extends Message> extends MessageOutConverter<String, M> {

    String convert(M message);
}
