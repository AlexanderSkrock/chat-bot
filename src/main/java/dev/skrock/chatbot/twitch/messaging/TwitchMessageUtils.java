package dev.skrock.chatbot.twitch.messaging;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class TwitchMessageUtils {

    public String channelParameter(String channel) {
        if (Strings.isBlank(channel)) {
            return channel;
        }

        return channel.startsWith("#") ? channel : "#" + channel;
    }
}
