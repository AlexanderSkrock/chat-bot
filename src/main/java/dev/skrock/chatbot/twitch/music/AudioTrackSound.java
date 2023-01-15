package dev.skrock.chatbot.twitch.music;

import com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.InternalAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import dev.skrock.chatbot.audio.Sound;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class AudioTrackSound implements Sound {

    private final InternalAudioTrack track;

    private byte[] currentFrame;
    private int currentPosition;

    public AudioTrackSound(AudioTrack track) {
        this.track = (InternalAudioTrack) track;

        this.currentFrame = null;
        this.currentPosition = 0;
    }

    @Override
    public String getName() {
        return track.getInfo().title;
    }

    @Override
    public MimeType getMimeType() {
        return MediaType.ALL;
    }

    @Override
    public InputStream getStream() {
        return new InputStream() {
            @Override
            public int read() {
                if (currentFrame == null || currentPosition >= currentFrame.length) {
                    MutableAudioFrame frame = new MutableAudioFrame();
                    track.provide(frame);
                    currentFrame = frame.getData();
                    currentPosition = 0;

                }
                if (currentFrame == null || currentPosition >= currentFrame.length) {
                    return -1;
                }
                return currentFrame[currentPosition++] & 0xFF;
            }
        };
    }
}
