package dev.skrock.chatbot.audio;

public interface Audio {

    AudioInfo getInfo();

    void load(AudioLoaderContext context);
}
