package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.Playlist;
import dev.skrock.chatbot.audio.Track;

public interface AudioLoadResultHandler {

    void onNoneFound(AudioDescriptor descriptor);

    void onTrackFound(AudioDescriptor descriptor, Track track);

    void onPlaylistFound(AudioDescriptor descriptor, Playlist playlist);
}
