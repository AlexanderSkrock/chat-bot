package dev.skrock.chatbot.audio;

import java.util.concurrent.LinkedBlockingDeque;

public class AudioPipe {

    private final LinkedBlockingDeque<Byte[]> bytes = new LinkedBlockingDeque<>();

    public InputPipe inputPipe() {
        throw new UnsupportedOperationException();
    }

    public OutputPipe outputPipe() {
        throw new UnsupportedOperationException();
    }

    private class InputPipe {

    }

    private class InputOutputPipePipe {

    }
}
