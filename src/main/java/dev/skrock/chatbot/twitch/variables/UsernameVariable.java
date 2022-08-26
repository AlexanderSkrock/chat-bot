package dev.skrock.chatbot.twitch.variables;

import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import org.springframework.stereotype.Component;

@Component
public class UsernameVariable implements TwitchMessageVariable {
    public static final String USERNAME_VARIABLE = "username";

    @Override
    public String name() {
        return USERNAME_VARIABLE;
    }

    @Override
    public String value(TwitchMessage message) {
        return message.getSource().getUserName();
    }
}
