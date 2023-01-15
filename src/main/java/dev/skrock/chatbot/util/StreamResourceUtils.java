package dev.skrock.chatbot.util;

import com.vaadin.flow.server.StreamResource;
import dev.skrock.chatbot.audio.Sound;
import dev.skrock.chatbot.storage.SoundEntity;
import lombok.experimental.UtilityClass;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@UtilityClass
public class StreamResourceUtils {
    public StreamResource ofSound(Sound sound) {
        return getStreamResource(sound.getName(), sound.getMimeType(), sound.getStream());
    }

    public StreamResource getStreamResource(String filename, MimeType mimeType, byte[] content) {
        return getStreamResource(filename, mimeType, new ByteArrayInputStream(content));
    }

    public StreamResource getStreamResource(String filename, MimeType mimeType, InputStream inputStream) {
        StreamResource streamResource = new StreamResource(filename, () -> inputStream);
        streamResource.setContentType(mimeType.toString());
        return streamResource;
    }
}
