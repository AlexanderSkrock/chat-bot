package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
public class StreamingSound implements Sound {

    private final String name;

    private final String mimeType;

    private final InputStream internalStream;

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
        return internalStream;
    }
}
