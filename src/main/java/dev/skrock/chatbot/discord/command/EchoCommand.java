package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import discord4j.core.object.entity.User;
import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Publisher;

public class EchoCommand implements DiscordResponseCommand {

    public static final String ECHO_COMMAND_TRIGGER = "echo";

    @Override
    public boolean matches(DiscordMessage message) {
        String text = message.getMessage().getContent();
        return Strings.isNotBlank(text) && text.startsWith(ECHO_COMMAND_TRIGGER);
    }

    @Override
    public Publisher<DiscordMessage> getResponse(DiscordMessage message) {
        String mention = message.getMessage().getAuthor().map(User::getMention).orElse("");
        String responseText = message.getMessage().getContent().replaceFirst(ECHO_COMMAND_TRIGGER, mention);
        return message.getMessage().getChannel().flatMap(channel -> channel.createMessage(responseText)).map(DiscordMessage::new);
    }
}
