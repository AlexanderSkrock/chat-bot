package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.core.object.entity.User;
import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Publisher;

public class EchoCommand implements DiscordChatCommand {

    public static final String ECHO_COMMAND_TRIGGER = "echo";

    @Override
    public boolean matches(DiscordMessageIn message) {
        String text = message.getMessageCreateEvent().getMessage().getContent();
        return Strings.isNotBlank(text) && text.startsWith(ECHO_COMMAND_TRIGGER);
    }

    @Override
    public Publisher<DiscordMessageOut> execute(DiscordMessageIn message, DiscordCommandContext commandContext) {
        String mention = message.getMessageCreateEvent().getMessage().getAuthor().map(User::getMention).orElse("");
        String responseText = message.getMessageCreateEvent().getMessage().getContent().replaceFirst(ECHO_COMMAND_TRIGGER, mention);
        return message.getMessageCreateEvent().getMessage().getChannel().flatMap(channel -> channel.createMessage(responseText)).map(DiscordMessageOut::new);
    }
}
