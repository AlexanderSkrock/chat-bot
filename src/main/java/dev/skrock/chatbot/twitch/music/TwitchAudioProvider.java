package dev.skrock.chatbot.twitch.music;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import discord4j.voice.AudioProvider;

import java.nio.ByteBuffer;

public class TwitchAudioProvider extends AudioProvider {

    private final AudioPlayer player;
    private final MutableAudioFrame frame;

    private final ChatBotUserInterface userInterface;

    public TwitchAudioProvider(final AudioPlayer player, ChatBotUserInterface userInterface) {
        // Allocate a ByteBuffer for Discord4J's AudioProvider to hold audio data
        // for Discord
        super(ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()));
        // Set MutableAudioFrame to use the same buffer as the one we just allocated
        frame = new MutableAudioFrame();
        frame.setBuffer(getBuffer());

        this.player = player;
        this.userInterface = userInterface;
    }

    @Override
    public boolean provide() {
        // AudioPlayer writes audio data to its AudioFrame
        final boolean didProvide = player.provide(frame);
        // If audio was provided, flip from write-mode to read-mode
        if (didProvide) {
            getBuffer().flip();
        }
        return didProvide;
    }
}
