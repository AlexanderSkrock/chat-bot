package dev.skrock.chatbot.twitch.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.audio.Sound;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;

public class TwitchAudioPlayer implements AudioPlayer {

    private final ChatBotUserInterface userInterface;

    private final AudioPlayerManager audioManager;

    public TwitchAudioPlayer(ChatBotUserInterface userInterface, AudioPlayerManager audioManager) {
        this.userInterface = userInterface;
        this.audioManager = audioManager;
    }


    @Override
    public void play(String url) {
        audioManager.loadItem(url, new TwitchAudioLoadResultHandler(userInterface));

    }

    @Override
    public void play(Sound sound) {
        userInterface.notifyUser(new SimpleChatBotUserNotification(sound));
    }
}
