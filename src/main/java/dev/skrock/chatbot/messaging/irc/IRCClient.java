package dev.skrock.chatbot.messaging.irc;

import dev.skrock.chatbot.messaging.AbstractClient;
import dev.skrock.chatbot.messaging.Message;

public abstract class IRCClient<Connection extends IRCConnection, M extends Message> extends AbstractClient<String, M, Connection> {

    public IRCClient(Connection connection, IRCMessageInConverter<M> inMessageConverter, IRCMessageOutConverter<M> outMessageConverter) {
        super(connection, inMessageConverter, outMessageConverter);
    }
}
