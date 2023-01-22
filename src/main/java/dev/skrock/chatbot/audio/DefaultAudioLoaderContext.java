package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;

import java.io.PipedOutputStream;

@AllArgsConstructor
public class DefaultAudioLoaderContext implements AudioLoaderContext {

    private final AudioPipe audioPipe;

    public AudioPipe.AudioOutputPipe outputPipe() {
        return audioPipe.outputPipe();
    }
}
