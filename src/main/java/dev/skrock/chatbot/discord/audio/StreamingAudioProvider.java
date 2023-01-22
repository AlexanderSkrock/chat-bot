package dev.skrock.chatbot.discord.audio;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import discord4j.voice.AudioProvider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class StreamingAudioProvider extends AudioProvider {

    private final InputStream inputStream;

    public StreamingAudioProvider(InputStream inputStream) {
        super(ByteBuffer.allocate(StandardAudioDataFormats.DISCORD_OPUS.maximumChunkSize()));
        this.inputStream = inputStream;
    }

    @Override
    public boolean provide() {
        int readLength = 0;
        try {
            byte[] data = new byte[getBuffer().limit()];
            readLength = inputStream.read(data);
            getBuffer().put(data, 0, readLength);
        } catch (IOException e) {
            readLength = -1;
        }

        boolean didProvide = readLength > 0;
        if (didProvide) {
            getBuffer().flip();
        }
        return didProvide;
    }
}
