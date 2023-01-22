package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.storage.SoundRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SoundRepositorySource implements AudioSource {

    private final SoundRepository soundRepository;

    @Override
    public AudioSearchResult search(AudioDescriptor descriptor) {
        return soundRepository
                .findByNameIgnoreCase(descriptor.getIdentifier())
                .map(SoundTrack::new)
                .map(AudioSearchResult::single)
                .orElseGet(AudioSearchResult::none);
    }
}
