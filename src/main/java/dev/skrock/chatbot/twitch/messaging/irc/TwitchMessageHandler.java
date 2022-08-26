package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.messaging.irc.IRCMessageHandler;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;

public interface TwitchMessageHandler extends IRCMessageHandler<TwitchIRCClient, TwitchMessage> {
}
