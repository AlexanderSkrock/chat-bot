package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;
import org.springframework.util.MimeType;

import java.io.InputStream;

@AllArgsConstructor
public class StreamingSound implements Sound {

    private final String name;

    private final MimeType mimeType;

    private final InputStream internalStream;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MimeType getMimeType() {
        return mimeType;
    }

    @Override
    public InputStream getStream() {
        return internalStream;
    }
}
