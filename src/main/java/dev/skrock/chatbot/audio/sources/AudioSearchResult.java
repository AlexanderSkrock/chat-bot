package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.Audio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface AudioSearchResult {

    static AudioSearchResult none() {
        return Collections::emptyList;
    }

    static AudioSearchResult single(Audio audio) {
        return () -> Collections.singletonList(audio);
    }

    static AudioSearchResult multiple(List<? extends Audio> audios) {
        return () -> Collections.unmodifiableList(audios);
    }


    List<Audio> getResults();

    default Optional<Audio> getTopResult() {
        if (isEmpty()) {
            return Optional.empty();
        }
        return getResults().stream().findFirst();
    }

    default boolean isEmpty() {
        return getResults() == null || getResults().isEmpty();
    }
}
