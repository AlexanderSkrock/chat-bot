package dev.skrock.chatbot.util;

import com.vaadin.flow.server.StreamResource;
import lombok.experimental.UtilityClass;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@UtilityClass
public class StreamResourceUtils {
    public StreamResource getStreamResource(String filename, MimeType mimeType, byte[] content) {
        return getStreamResource(filename, mimeType, new ByteArrayInputStream(content));
    }

    public StreamResource getStreamResource(String filename, MimeType mimeType, InputStream inputStream) {
        StreamResource streamResource = new StreamResource(filename, () -> inputStream);
        streamResource.setContentType(mimeType.toString());
        return streamResource;
    }
}
