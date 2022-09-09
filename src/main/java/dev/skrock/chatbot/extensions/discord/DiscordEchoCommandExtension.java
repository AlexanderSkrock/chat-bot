package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.discord.DiscordChatBotExtension;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.core.object.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DiscordEchoCommandExtension implements DiscordChatBotExtension {

    @Override
    public void onBeforeInit(DiscordChatBot bot) {

    }

    @Override
    public void onAfterInit(DiscordChatBot bot) {
        if (!bot.supportsCommands()) {
            log.info("The bot does not support commands. Hence, this extension will be disabled.");
        }
        bot.addCommand(new EchoCommand());
    }

    @Override
    public void onBeforeStart(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStart(DiscordChatBot bot) {

    }

    @Override
    public void onBeforeStop(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStop(DiscordChatBot bot) {

    }

    private static class EchoCommand implements DiscordChatCommand {

        public static final String ECHO_COMMAND_TRIGGER = "echo";

        @Override
        public boolean matches(DiscordMessageIn message) {
            String text = message.getMessageCreateEvent().getMessage().getContent();
            return Strings.isNotBlank(text) && text.startsWith(ECHO_COMMAND_TRIGGER);
        }

        @Override
        public Mono<DiscordMessageOut> execute(DiscordMessageIn message, DiscordCommandContext commandContext) {
            String mention = message.getMessageCreateEvent().getMessage().getAuthor().map(User::getMention).orElse("");
            String responseText = message.getMessageCreateEvent().getMessage().getContent().replaceFirst(ECHO_COMMAND_TRIGGER, mention);
            return message.getMessageCreateEvent().getMessage().getChannel().flatMap(channel -> channel.createMessage(responseText)).map(DiscordMessageOut::new);
        }
    }
}
