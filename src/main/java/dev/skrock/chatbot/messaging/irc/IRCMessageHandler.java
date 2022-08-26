package dev.skrock.chatbot.messaging.irc;

import dev.skrock.chatbot.messaging.Message;
import dev.skrock.chatbot.messaging.MessageHandler;

public interface IRCMessageHandler<Client extends IRCClient<?, M>, M extends Message> extends MessageHandler<Client, M> {

    void handle(M message, Client ircClient);
}
