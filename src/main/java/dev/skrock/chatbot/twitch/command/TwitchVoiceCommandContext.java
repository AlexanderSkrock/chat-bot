package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.twitch.music.TwitchAudioPlayer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;

public class TwitchVoiceCommandContext extends TwitchCommandContext implements SupportsAudioPlayback {

    public TwitchVoiceCommandContext(ChatBotUserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public AudioPlayer getAudioPlayer() {
        return new TwitchAudioPlayer(getUserInterface());
    }
}
