package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;

public interface DiscordChatCommand extends Command<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> {
}
