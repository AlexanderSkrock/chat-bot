package dev.skrock.chatbot.twitch.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Extracted from example parser on: https://dev.twitch.tv/docs/irc/example-parser
 */
@Getter
@AllArgsConstructor
public enum TwitchCommands {
    // IRC
    PASS("PASS"),
    NICK("NICK"),
    PING("PING"),
    PONG("PONG"),

    // TWITCH
    JOIN("JOIN"),
    PART("PART"),
    NOTICE("NOTICE"),
    CLEARCHAT("CLEARCHAT"),
    HOSTTARGET("HOSTTARGET"),
    PRIVMSG("PRIVMSG"),
    CAP("CAP"),
    GLOBALUSERSTATE("GLOBALUSERSTATE"), // Included only if you request the /commands capability, but it has no meaning without also including the /tags capability.
    USERSTATE("USERSTATE"),
    ROOMSTATE("ROOMSTATE"),
    RECONNECT("RECONNECT"), // The Twitch IRC server is about to terminate the connection for maintenance.
    UNSUPPORTED("421"),
    SUCCESSFUL_LOGIN("001"), // Logged in (successfully authenticated).
    NUMERIC_002("002"), // Ignoring all other numeric messages.
    NUMERIC_003("003"),
    NUMERIC_004("004"),
    NUMERIC_353("353"), // Tells you who else is in the chat room you're joining.
    NUMERIC_366("366"),
    NUMERIC_372("372"),
    NUMERIC_375("375"),
    NUMERIC_376("376"),
    UNEXPECTED(null);

    private String name;

    public static TwitchCommands ofName(String commandName) {
        return Arrays
                .stream(TwitchCommands.values())
                .filter(command -> Objects.equals(command.getName(), commandName))
                .findFirst()
                .orElse(UNEXPECTED);
    }
}
