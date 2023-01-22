package dev.skrock.chatbot.audio.sources;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import dev.skrock.chatbot.audio.Audio;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class YoutubeSource implements AudioSource {

    private static final long MAX_SEARCH_RESULTS = 1L;

    private static final String VIDEO_TYPE = "video";

    private YouTube youTube;

    @Override
    public AudioSearchResult search(AudioDescriptor descriptor) {
        try {
            SearchListResponse response = youTube
                    .search()
                    .list(List.of("id", "snippet"))
                    .setQ(descriptor.getIdentifier())
                    .setType(List.of(VIDEO_TYPE))
                    .setMaxResults(MAX_SEARCH_RESULTS)
                    .execute();

            return AudioSearchResult.multiple(extractAudios(response));
        } catch (IOException e) {
            log.error("Fehler bei der Abfrage von Youtube", e);
            return AudioSearchResult.none();
        }
    }

    protected List<? extends Audio> extractAudios(SearchListResponse response) {
        return response
                .getItems()
                .stream()
                .map(this::createTrack)
                .toList();
    }

    protected YoutubeTrack createTrack(SearchResult searchResult) {
        return new YoutubeTrack(
                searchResult.getId().getVideoId(),
                searchResult.getSnippet().getTitle(),
                searchResult.getSnippet().getChannelTitle()
        );
    }
}
