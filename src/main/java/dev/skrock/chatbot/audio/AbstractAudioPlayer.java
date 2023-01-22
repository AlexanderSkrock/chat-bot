package dev.skrock.chatbot.audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public abstract class AbstractAudioPlayer implements AudioPlayer {

    private PipedInputStream inputPipe;
    private PipedOutputStream outputPipe;

    @Override
    public void play(Track track) {
        initPipes();
        AudioLoaderContext loaderContext = new DefaultAudioLoaderContext(outputPipe);
        track.load(loaderContext);
    }

    protected InputStream getInputStream() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return inputPipe.read();
            }
        };
    }

    protected int getPipeSize() {
        return StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize();
    }

    private void initPipes() {
        try {
            inputPipe = new PipedInputStream(getPipeSize());
            outputPipe = new PipedOutputStream(inputPipe);
        } catch (IOException e) {
            log.error("Fehler bei der Initialisierung der Audio Pipe", e);
        }
    }
}
