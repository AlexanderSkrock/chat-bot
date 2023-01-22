package dev.skrock.chatbot.discord.audio;

import dev.skrock.chatbot.audio.AbstractAudioPlayer;
import discord4j.voice.AudioProvider;

public class DiscordAudioPlayer extends AbstractAudioPlayer {

    public AudioProvider getAudioProvider() {
        return new StreamingAudioProvider(getInputStream());
    }
}
