package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.Audio;
import dev.skrock.chatbot.audio.Playlist;
import dev.skrock.chatbot.audio.Track;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AudioLoader {

    private final List<AudioSource> sources = new LinkedList<>();

    public AudioLoader(List<AudioSource> sources) {
        if (sources != null) {
            this.sources.addAll(sources);
        }
    }

    public void load(AudioDescriptor descriptor, AudioLoadResultHandler resultHandler) {
        Optional<Audio> topSearchResult = sources
                .stream()
                .map(source -> source.search(descriptor))
                .flatMap(result -> result.getTopResult().stream())
                .findFirst();

        topSearchResult.ifPresentOrElse(
                audio -> {
                    if (audio instanceof Track track) {
                        resultHandler.onTrackFound(descriptor, track);
                    } else if (audio instanceof Playlist playlist) {
                        resultHandler.onPlaylistFound(descriptor, playlist);
                    }
                },
                () -> resultHandler.onNoneFound(descriptor)
        );
    }
}
