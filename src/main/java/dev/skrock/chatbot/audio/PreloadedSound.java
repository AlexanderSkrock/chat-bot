package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@AllArgsConstructor
public class PreloadedSound implements Sound {

    private final String name;

    private final MimeType mimeType;

    private final byte[] data;

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
        return new ByteArrayInputStream(data);
    }
}
