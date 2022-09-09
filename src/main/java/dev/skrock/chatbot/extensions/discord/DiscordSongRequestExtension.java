package dev.skrock.chatbot.extensions.discord;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.discord.DiscordChatBotExtension;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import dev.skrock.chatbot.discord.music.LavaPlayerAudioProvider;
import dev.skrock.chatbot.discord.music.TrackScheduler;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.AudioProvider;
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
            AudioPlayer audioPlayer = commandContext.getAudioPlayerManager().createPlayer();
            AudioProvider audioProvider = new LavaPlayerAudioProvider(audioPlayer);
            AudioLoadResultHandler audioLoadResultHandler = new TrackScheduler(audioPlayer);
            // TODO move implementation specifics (audio provider and load result handler) to command context

            Mono<Object> joinMono = Mono.justOrEmpty(message.getMessageCreateEvent().getMember())
                    .flatMap(Member::getVoiceState)
                    .flatMap(VoiceState::getChannel)
                    // FIXME close open connections
                    // join returns a VoiceConnection which would be required if we were
                    // adding disconnection features, but for now we are just ignoring it.
                    .flatMap(channel -> channel.join(VoiceChannelJoinSpec.builder().provider(audioProvider).build()));

            Mono<Void> playMono = Mono.justOrEmpty(message.getMessageCreateEvent().getMessage().getContent())
                    .map(content -> StringUtils.substringAfter(content, SONG_REQUEST_COMMAND_TRIGGER + " "))
                    .doOnNext(urlParam -> commandContext.getAudioPlayerManager().loadItem(urlParam, audioLoadResultHandler))
                    .then();

            return joinMono.then(playMono).then(Mono.empty());
        }
    }
}
