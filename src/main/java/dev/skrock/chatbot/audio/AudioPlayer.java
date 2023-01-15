package dev.skrock.chatbot.audio;

public interface AudioPlayer {

    void play(String url);

    void play(Sound sound);
}
