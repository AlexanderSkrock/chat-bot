package dev.skrock.chatbot.audio;

import java.io.PipedOutputStream;

public interface AudioLoaderContext {

    PipedOutputStream outputPipe();
}
