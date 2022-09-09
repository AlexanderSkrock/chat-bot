package dev.skrock.chatbot.discord.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscordCommandExecutor implements CommandExecutor<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> {

    private final AudioPlayerManager audioPlayerManager;

    @Autowired
    public DiscordCommandExecutor(AudioPlayerManager audioPlayerManager) {
        this.audioPlayerManager = audioPlayerManager;
    }

    @Override
    public DiscordCommandContext provideContextForMessage(DiscordMessageIn message) {
        return new DiscordCommandContext(audioPlayerManager);
    }
}
