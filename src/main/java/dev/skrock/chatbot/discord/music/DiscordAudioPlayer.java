package dev.skrock.chatbot.discord.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.audio.Sound;

public class DiscordAudioPlayer implements AudioPlayer {

    private final AudioPlayerManager audioPlayerManager;
    private final com.sedmelluq.discord.lavaplayer.player.AudioPlayer player;

    public DiscordAudioPlayer(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
        this.player = audioPlayerManager.createPlayer();
    }

    public com.sedmelluq.discord.lavaplayer.player.AudioPlayer getInternalPlayer() {
        return player;
    }

    @Override
    public void play(String url) {
        audioPlayerManager.loadItem(url, new TrackScheduler(player));
    }

    @Override
    public void play(Sound sound) {
        throw new UnsupportedOperationException();
    }
}
