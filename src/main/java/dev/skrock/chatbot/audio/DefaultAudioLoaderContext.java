package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;

import java.io.PipedOutputStream;

@AllArgsConstructor
public class DefaultAudioLoaderContext implements AudioLoaderContext {

    private final PipedOutputStream output;

    public PipedOutputStream outputPipe() {
        return output;
    }
}
