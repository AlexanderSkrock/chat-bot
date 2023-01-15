package dev.skrock.chatbot.twitch.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@AllArgsConstructor
public class TwitchAudioLoadResultHandler implements AudioLoadResultHandler {

    private final ChatBotUserInterface userInterface;

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        userInterface.notifyUser(new SimpleChatBotUserNotification(new AudioTrackSound(audioTrack)));
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        Optional.ofNullable(audioPlaylist.getSelectedTrack())
                .or(() -> CollectionUtils.isEmpty(audioPlaylist.getTracks()) ? Optional.empty() : Optional.of(audioPlaylist.getTracks().get(0)))
                .ifPresent(this::trackLoaded);

    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException e) {

    }
}
