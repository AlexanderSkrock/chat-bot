package dev.skrock.chatbot.twitch.music;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.audio.Sound;
import dev.skrock.chatbot.storage.SoundEntity;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TwitchAudioPlayer implements AudioPlayer {

    private final ChatBotUserInterface userInterface;

    @Override
    public void play(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void play(Sound sound) {
        userInterface.notifyUser(new SimpleChatBotUserNotification(sound));
    }
}
