package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import org.apache.logging.log4j.util.Strings;

public class EchoCommand implements TwitchResponseCommand {

    public static final String ECHO_COMMAND_TRIGGER = "echo";

    @Override
    public boolean matches(PrivMsgMessage message) {
        String text = message.getMessage();
        return Strings.isNotBlank(text) && text.startsWith(ECHO_COMMAND_TRIGGER);
    }

    @Override
    public PrivMsgMessage getResponse(PrivMsgMessage message) {
        String responseText = message.getMessage().replace(ECHO_COMMAND_TRIGGER, "@" + message.getSource().getUserName());
        return new PrivMsgMessage(message.getChannel(), responseText);
    }
}
