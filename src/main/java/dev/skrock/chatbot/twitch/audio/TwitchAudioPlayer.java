package dev.skrock.chatbot.twitch.audio;

import dev.skrock.chatbot.audio.AbstractAudioPlayer;
import dev.skrock.chatbot.audio.Track;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;

public class TwitchAudioPlayer extends AbstractAudioPlayer {

    private final ChatBotUserInterface userInterface;

    public TwitchAudioPlayer(ChatBotUserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void play(Track track) {
        super.play(track);
        userInterface.notifyUser(new SimpleChatBotUserNotification(
                track.getInfo().getAuthor() + " - " + track.getInfo().getName(),
                getInputStream()
        ));
    }
}
