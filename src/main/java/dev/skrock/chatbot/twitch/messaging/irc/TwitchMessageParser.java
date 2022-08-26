package dev.skrock.chatbot.twitch.messaging.irc;

import dev.skrock.chatbot.twitch.messaging.TwitchCommands;
import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * Twitch-Documentation
 * - IRC https://dev.twitch.tv/docs/irc
 * - Message Parsing https://dev.twitch.tv/docs/irc/example-parser#parser-output
 */
@Slf4j
public class TwitchMessageParser {

    // private static final Pattern TWITCH_MESSAGE_PATTERN = Pattern.compile(":(?<source>[^:]*):(?<message>.*)");

    // Bsp.: :lovingt3s!lovingt3s@lovingt3s.tmi.twitch.tv PRIVMSG #lovingt3s :!dilly
    //
    // @tags :source command parameters :message
    // :source command:parameters
    // :sourceNurMitHost command:parameters
    // command:parameters
    // command
    // source=nickname!host
    private static final Pattern TWITCH_MESSAGE_PATTERN = Pattern.compile("(?:@(?<tags>[^ ]*) )?(?::(?:(?<username>[^! ]+)!)?(?<hostname>[^ ]+) )?(?<command>[^ ]*)(?: (?<parameters>[^:]*))?(?::(?<message>.+))?");

    // Erstmal nur vereinfach wie folgt
    // Bsp.: :lovingt3s!lovingt3s@lovingt3s.tmi.twitch.tv PRIVMSG #lovingt3s :!dilly
    public TwitchMessage parse(String rawMessage) {
        Matcher messageMatcher = TWITCH_MESSAGE_PATTERN.matcher(rawMessage);
        if (!messageMatcher.matches()) {
            throw new IllegalArgumentException("unsupported raw message: " + rawMessage);
        }

        TwitchMessage.Tags tags = new TwitchMessage.Tags(); // Lassen wir erstmal immer leer
        String rawTags = messageMatcher.group("tags");
        if (Strings.isNotBlank(rawTags)) {
            String[] rawTagElements = rawTags.split(";");
            for (String rawTagElement : rawTagElements) {
                String[] tagSegments = rawTagElement.split("=");
                if (tagSegments.length != 2) {
                    log.warn("ungültiges Tag-Element gefunden: {}", rawTagElement);
                    continue;
                }
                tags.put(tagSegments[0], tagSegments[1]);
            }
        }

        TwitchMessage.Source source = new TwitchMessage.Source();
        source.setUserName(messageMatcher.group("username"));
        source.setHostName(messageMatcher.group("hostname"));

        String commandName = messageMatcher.group("command");
        TwitchCommands command = TwitchCommands.ofName(commandName);
        if (command == TwitchCommands.UNEXPECTED || command == TwitchCommands.UNSUPPORTED) {
            String message = String.format("unsupported message command '%s' in raw message: %s", commandName, rawMessage);
            throw new IllegalArgumentException(message);
        };

        TwitchMessage.TwitchIRCCommandParameters commandParameters = new TwitchMessage.TwitchIRCCommandParameters();
        String parameterString = messageMatcher.group("parameters");
        if (Strings.isNotBlank(parameterString)) {
            String[] rawParameters = parameterString.split(" ");
            commandParameters.addAll(Arrays.asList(rawParameters));
        }

        String message = messageMatcher.group("message");

        return new TwitchMessage(tags, source, command, commandParameters, message);
    }

    public String format(TwitchMessage message) {
        String tagsString = message.getTags().isEmpty()
                ? ""
                : "@" + message.getTags().entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(";"));

        String sourceString = formatSource(message.getSource());

        String commandString = message.getCommand().getName();

        String commandParameters = formatCommandParameters(message.getParameters());

        String messageText = Strings.isNotBlank(message.getMessage()) ? ":" + message.getMessage() : "";

        return Stream
                .of(tagsString, sourceString, commandString, commandParameters, messageText)
                .filter(Strings::isNotBlank)
                .collect(Collectors.joining(" "));
    }

    private String formatSource(TwitchMessage.Source source) {
        if (Strings.isBlank(source.getUserName())) {
            return Strings.isBlank(source.getHostName()) ? "" : source.getHostName();
        }
        return source.getUserName() + "!" + source.getHostName();
    }

    private String formatCommandParameters(TwitchMessage.TwitchIRCCommandParameters commandParameters) {
        if (commandParameters.isEmpty()) {
            return "";
        }
        return String.join(" ", commandParameters);
    }
}
