package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import discord4j.core.event.domain.message.MessageCreateEvent;

public interface DiscordChatCommand extends Command<DiscordMessage> {
}
