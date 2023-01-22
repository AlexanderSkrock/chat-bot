package dev.skrock.chatbot.audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public abstract class AbstractAudioPlayer implements AudioPlayer {

    private final AudioPipe audioPipe = new AudioPipe();

    @Override
    public void play(Track track) {
        AudioLoaderContext loaderContext = new DefaultAudioLoaderContext(audioPipe);
        track.load(loaderContext);
    }

    protected InputStream getInputStream() {
        return audioPipe.inputPipe();
    }
}
