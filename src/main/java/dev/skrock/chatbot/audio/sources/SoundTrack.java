package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.AudioInfo;
import dev.skrock.chatbot.audio.AudioLoaderContext;
import dev.skrock.chatbot.audio.SimpleAudioInfo;
import dev.skrock.chatbot.audio.Track;
import dev.skrock.chatbot.storage.SoundEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class SoundTrack implements Track {

    private final SoundEntity sound;

    @Override
    public AudioInfo getInfo() {
        return SimpleAudioInfo.builder().name(sound.getName()).build();
    }

    @Override
    public void load(AudioLoaderContext context) {
        try {
            context.outputPipe().write(sound.getData());
        } catch (IOException e) {
            log.error("Fehler beim Laden des Sounds", e);
        }
    }
}
