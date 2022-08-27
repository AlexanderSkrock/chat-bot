package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.command.SoundCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface DiscordSoundCommand extends DiscordChatCommand, SoundCommand<DiscordMessage> {
}
