package dev.skrock.chatbot.discord.command;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import dev.skrock.chatbot.command.CommandContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiscordCommandContext implements CommandContext {

    private final AudioPlayerManager audioPlayerManager;
}
