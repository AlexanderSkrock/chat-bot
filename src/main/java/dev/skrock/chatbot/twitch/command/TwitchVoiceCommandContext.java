package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.audio.sources.AudioLoader;
import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.twitch.audio.TwitchAudioPlayer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;

public class TwitchVoiceCommandContext extends TwitchCommandContext implements SupportsAudioPlayback {

    private final AudioLoader audioLoader;

    public TwitchVoiceCommandContext(AudioLoader audioLoader, ChatBotUserInterface userInterface) {
        super(userInterface);
        this.audioLoader = audioLoader;
    }

    @Override
    public AudioLoader getAudioLoader() {
        return audioLoader;
    }

    @Override
    public AudioPlayer getAudioPlayer() {
        return new TwitchAudioPlayer(getUserInterface());
    }
}
