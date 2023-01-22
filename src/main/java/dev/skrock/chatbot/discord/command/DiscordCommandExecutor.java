package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.audio.sources.AudioLoader;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.discord.audio.DiscordAudioPlayer;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.common.util.Snowflake;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PartialMember;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.spec.VoiceChannelJoinSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DiscordCommandExecutor implements CommandExecutor<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> {

    private final AudioLoader audioLoader;

    private final ConcurrentMap<Long, DiscordAudioPlayer> audioPlayers;

    @Autowired
    public DiscordCommandExecutor(AudioLoader audioLoader) {
        this.audioLoader = audioLoader;
        this.audioPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public DiscordCommandContext provideContextForMessage(DiscordMessageIn message) {
        return getAudioPlayer(message)
                .<DiscordCommandContext>map(player -> new DiscordVoiceCommandContext(audioLoader, player))
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

        DiscordAudioPlayer player = audioPlayers.computeIfAbsent(guildId.get(), id -> new DiscordAudioPlayer());

        Optional<Snowflake> selfChannelId = message.getMessageCreateEvent().getGuild().flatMap(Guild::getSelfMember).flatMap(PartialMember::getVoiceState).map(VoiceState::getChannelId).blockOptional().orElseGet(Optional::empty);
        if (selfChannelId.filter(selfId -> Objects.equals(selfId, optMemberVoiceState.getChannelId().get())).isEmpty()) {
            VoiceChannel voiceChannel = optMemberVoiceState.getChannel().block();
            voiceChannel.join(VoiceChannelJoinSpec.builder().provider(player.getAudioProvider()).build()).block();
        }

        return Optional.of(player);
    }
}
