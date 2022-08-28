package dev.skrock.chatbot.discord.command;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import dev.skrock.chatbot.discord.music.LavaPlayerAudioProvider;
import dev.skrock.chatbot.discord.music.TrackScheduler;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.spec.VoiceChannelJoinSpec;
import discord4j.voice.AudioProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class SongRequestCommand implements DiscordChatCommand {

    public static final String SONG_REQUEST_COMMAND_TRIGGER = "sr";

    @Override
    public boolean matches(DiscordMessageIn message) {
        String text = message.getMessageCreateEvent().getMessage().getContent();
        return Strings.isNotBlank(text) && text.startsWith(SONG_REQUEST_COMMAND_TRIGGER);
    }

    @Override
    public Publisher<DiscordMessageOut> execute(DiscordMessageIn message, DiscordCommandContext commandContext) {
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
