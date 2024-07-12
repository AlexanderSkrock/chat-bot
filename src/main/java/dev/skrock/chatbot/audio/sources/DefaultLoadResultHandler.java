package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.audio.Playlist;
import dev.skrock.chatbot.audio.Track;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DefaultLoadResultHandler implements AudioLoadResultHandler {

    private final AudioPlayer player;

    @Override
    public void onNoneFound(AudioDescriptor descriptor) {
        log.info("Keinen Track gefunden: {}", descriptor);
    }

    @Override
    public void onTrackFound(AudioDescriptor descriptor, Track track) {
        player.play(track);
    }

    @Override
    public void onPlaylistFound(AudioDescriptor descriptor, Playlist playlist) {
        throw new UnsupportedOperationException();
    }
}
