package dev.skrock.chatbot.audio;

import java.io.InputStream;

public interface Sound {

    String getName();

    String getMimeType();

    InputStream getStream();
}
