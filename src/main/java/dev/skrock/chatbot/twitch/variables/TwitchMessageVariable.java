package dev.skrock.chatbot.twitch.variables;

import dev.skrock.chatbot.twitch.messaging.TwitchMessage;

public interface TwitchMessageVariable extends ContextualVariable<TwitchMessage> {
    String name();
    String value(TwitchMessage message);
}
