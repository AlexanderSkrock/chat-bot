package dev.skrock.chatbot.command;

import dev.skrock.chatbot.audio.AudioPlayer;

public interface SupportsAudioPlayback {

    AudioPlayer getAudioPlayer();
}
