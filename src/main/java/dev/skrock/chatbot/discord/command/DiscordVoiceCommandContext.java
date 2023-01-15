package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DiscordVoiceCommandContext extends DiscordCommandContext implements SupportsAudioPlayback {

    private final AudioPlayer audioPlayer;

    @Override
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
