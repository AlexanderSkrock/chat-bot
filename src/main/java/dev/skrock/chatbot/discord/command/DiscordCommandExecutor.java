package dev.skrock.chatbot.discord.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import dev.skrock.chatbot.discord.music.DiscordAudioPlayer;
import dev.skrock.chatbot.discord.music.LavaPlayerAudioProvider;
import discord4j.common.util.Snowflake;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PartialMember;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DiscordCommandExecutor implements CommandExecutor<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> {

    private final AudioPlayerManager audioPlayerManager;

    private final ConcurrentMap<Long, DiscordAudioPlayer> audioPlayers;

    @Autowired
    public DiscordCommandExecutor(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
        this.audioPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public DiscordCommandContext provideContextForMessage(DiscordMessageIn message) {
        return getAudioPlayer(message)
                .<DiscordCommandContext>map(DiscordVoiceCommandContext::new)
                .orElseGet(DiscordCommandContext::new);
    }

    protected Optional<AudioPlayer> getAudioPlayer(DiscordMessageIn message) {
        Optional<Long> guildId = message.getMessageCreateEvent().getGuildId().map(Snowflake::asLong);
        if (guildId.isEmpty()) {
            return Optional.empty();
        }

        Optional<Member> optMember = message.getMessageCreateEvent().getMember();
        if (optMember.isEmpty()) {
            return Optional.empty();
        }
        VoiceState optMemberVoiceState = optMember.get().getVoiceState().block();
        if (optMemberVoiceState.getChannelId().isEmpty()) {
            return Optional.empty();
        }

        DiscordAudioPlayer player = audioPlayers.computeIfAbsent(guildId.get(), id -> new DiscordAudioPlayer(audioPlayerManager));

        Optional<Snowflake> selfChannelId = message.getMessageCreateEvent().getGuild().flatMap(Guild::getSelfMember).flatMap(PartialMember::getVoiceState).map(VoiceState::getChannelId).blockOptional().orElseGet(Optional::empty);
        if (selfChannelId.filter(selfId -> Objects.equals(selfId, optMemberVoiceState.getChannelId().get())).isEmpty()) {
            VoiceChannel voiceChannel = optMemberVoiceState.getChannel().block();
            voiceChannel.join(VoiceChannelJoinSpec.builder().provider(new LavaPlayerAudioProvider(player.getInternalPlayer())).build()).block();
        }

        return Optional.of(player);
    }
}
