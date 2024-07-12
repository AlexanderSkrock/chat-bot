package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.audio.sources.DefaultLoadResultHandler;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.discord.DiscordChatBotExtension;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class DiscordSongRequestExtension implements DiscordChatBotExtension {

    @Override
    public void onBeforeInit(DiscordChatBot bot) {

    }

    @Override
    public void onAfterInit(DiscordChatBot bot) {
        if (!bot.supportsCommands()) {
            log.info("The bot does not support commands. Hence, this extension will be disabled.");
        }
        bot.addCommand(new SongRequestCommand());
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

    private static class SongRequestCommand implements DiscordChatCommand {

        public static final String SONG_REQUEST_COMMAND_TRIGGER = "sr";

        @Override
        public boolean matches(DiscordMessageIn message) {
            String text = message.getMessageCreateEvent().getMessage().getContent();
            return Strings.isNotBlank(text) && text.startsWith(SONG_REQUEST_COMMAND_TRIGGER);
        }

        @Override
        public Mono<DiscordMessageOut> execute(DiscordMessageIn message, DiscordCommandContext commandContext) {
            if (commandContext instanceof SupportsAudioPlayback soundContext) {
                String requestedAudioString = StringUtils.substringAfter(message.getMessageCreateEvent().getMessage().getContent(), SONG_REQUEST_COMMAND_TRIGGER + " ");
                soundContext.getAudioLoader().load(() -> requestedAudioString, new DefaultLoadResultHandler(soundContext.getAudioPlayer()));
            }
            return Mono.empty();
        }
    }
}
