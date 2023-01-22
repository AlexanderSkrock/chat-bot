package dev.skrock.chatbot.extensions.twitch;

import dev.skrock.chatbot.audio.sources.DefaultLoadResultHandler;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.core.twitch.TwitchChatBot;
import dev.skrock.chatbot.core.twitch.TwitchChatBotExtension;
import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.twitch.command.TwitchCommandContext;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TwitchSongRequestExtension implements TwitchChatBotExtension {

    @Override
    public void onBeforeInit(TwitchChatBot bot) {

    }

    @Override
    public void onAfterInit(TwitchChatBot bot) {
        if (!bot.supportsCommands()) {
            log.info("The bot does not support commands. Hence, this extension will be disabled.");
        }
        bot.addCommand(new SongRequestCommand());
    }

    @Override
    public void onBeforeStart(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStart(TwitchChatBot bot) {

    }

    @Override
    public void onBeforeStop(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStop(TwitchChatBot bot) {

    }

    private static class SongRequestCommand implements TwitchChatCommand {

        public static final String SONG_REQUEST_COMMAND_TRIGGER = "sr";

        @Override
        public boolean matches(PrivMsgMessage message) {
            String text = message.getMessage();
            return Strings.isNotBlank(text) && text.startsWith(SONG_REQUEST_COMMAND_TRIGGER);
        }

        @Override
        public Mono<PrivMsgMessage> execute(PrivMsgMessage message, TwitchCommandContext commandContext) {
            if (commandContext instanceof SupportsAudioPlayback soundContext) {
                String requestedAudioString = StringUtils.substringAfter(message.getMessage(), SONG_REQUEST_COMMAND_TRIGGER + " ");
                soundContext.getAudioLoader().load(() -> requestedAudioString, new DefaultLoadResultHandler(soundContext.getAudioPlayer()));

            }
            return Mono.empty();
        }
    }
}
