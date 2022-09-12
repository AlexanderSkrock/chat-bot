package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.SupportsAudioPlayback;

public class DiscordVoiceCommandContext extends DiscordCommandContext implements SupportsAudioPlayback {

    private final AudioPlayer audioPlayer;

    public DiscordVoiceCommandContext(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
