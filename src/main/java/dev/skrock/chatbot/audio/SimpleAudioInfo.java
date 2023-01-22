package dev.skrock.chatbot.audio;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class SimpleAudioInfo implements AudioInfo {

    private final String name;

    private final String author;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAuthor() {
        return null;
    }
}
