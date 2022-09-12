package dev.skrock.chatbot.audio;

import dev.skrock.chatbot.storage.Sound;

public interface AudioPlayer {

    void play(String url);

    void play(Sound sound);
}
