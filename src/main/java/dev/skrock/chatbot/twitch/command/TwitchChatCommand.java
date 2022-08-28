package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface TwitchChatCommand extends Command<TwitchCommandContext, PrivMsgMessage, PrivMsgMessage> {
}
