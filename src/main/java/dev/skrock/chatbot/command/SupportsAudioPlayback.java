package dev.skrock.chatbot.command;

import dev.skrock.chatbot.audio.sources.AudioLoader;
import dev.skrock.chatbot.audio.AudioPlayer;

public interface SupportsAudioPlayback {

    AudioLoader getAudioLoader();

    AudioPlayer getAudioPlayer();
}
