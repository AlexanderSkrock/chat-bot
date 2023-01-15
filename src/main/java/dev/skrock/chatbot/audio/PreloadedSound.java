package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@AllArgsConstructor
public class PreloadedSound implements Sound {

    private final String name;

    private final String mimeType;

    private final byte[] data;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(data);
    }
}
