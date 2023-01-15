package dev.skrock.chatbot.twitch.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.twitch.music.TwitchAudioPlayer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;

public class TwitchVoiceCommandContext extends TwitchCommandContext implements SupportsAudioPlayback {

    private final AudioPlayerManager audioPlayerManager;

    public TwitchVoiceCommandContext(ChatBotUserInterface userInterface, AudioPlayerManager audioPlayerManager) {
        super(userInterface);
        this.audioPlayerManager = audioPlayerManager;
    }

    @Override
    public AudioPlayer getAudioPlayer() {
        return new TwitchAudioPlayer(getUserInterface(), audioPlayerManager);
    }
}
