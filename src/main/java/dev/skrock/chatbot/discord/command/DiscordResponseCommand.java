package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.command.ResponseCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;

public interface DiscordResponseCommand extends DiscordChatCommand, ResponseCommand<DiscordMessage> {
}
