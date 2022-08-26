package dev.skrock.chatbot.twitch.messaging;

import org.apache.logging.log4j.util.Strings;

public class OAuthPassMessage extends PassMessage {
    public static final String OAUTH_PREFIX = "";

    public static String ensurePrefix(String token) {
        if (Strings.isBlank(token)) {
            return null;
        }
        return token.startsWith(OAUTH_PREFIX) ? token : OAUTH_PREFIX + token;
    }

    public OAuthPassMessage(String token) {
        super(ensurePrefix(token));
    }
}
