package dev.skrock.chatbot.audio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

public class AudioPipe {

    private final LinkedBlockingDeque<Integer> bytes = new LinkedBlockingDeque<>();

    public AudioInputPipe inputPipe() {
        return new AudioInputPipe();
    }

    public AudioOutputPipe outputPipe() {
        return new AudioOutputPipe();
    }

    public class AudioInputPipe extends InputStream {

        @Override
        public int read() throws IOException {
            return Optional
                    .ofNullable(bytes.pollFirst())
                    .map(b -> b & 0xff)
                    .orElse(0);
        }
    }

    public class AudioOutputPipe extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            bytes.add(b);
        }
    }
}
