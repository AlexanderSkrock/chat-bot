package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.command.ActionCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface DiscordActionCommand extends DiscordChatCommand, ActionCommand<DiscordMessage> {
}
