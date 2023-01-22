package dev.skrock.chatbot.audio.sources;

import dev.skrock.chatbot.audio.AudioInfo;
import dev.skrock.chatbot.audio.AudioLoaderContext;
import dev.skrock.chatbot.audio.SimpleAudioInfo;
import dev.skrock.chatbot.audio.Track;

public class YoutubeTrack implements Track {

    private final String videoId;

    private final String title;
    private final String channelName;

    public YoutubeTrack(String videoId, String title, String channelName) {
        this.videoId = videoId;
        this.title = title;
        this.channelName = channelName;
    }

    @Override
    public AudioInfo getInfo() {
        return SimpleAudioInfo
                .builder()
                .name(title)
                .author(channelName)
                .build();
    }

    @Override
    public void load(AudioLoaderContext context) {
        // TODO implement loading audio
        throw new UnsupportedOperationException();
    }
}
