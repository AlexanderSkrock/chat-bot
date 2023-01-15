package dev.skrock.chatbot.audio;

import org.springframework.util.MimeType;

import java.io.InputStream;

public interface Sound {

    String getName();

    MimeType getMimeType();

    InputStream getStream();
}
