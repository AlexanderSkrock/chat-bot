package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.ResponseCommand;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface TwitchResponseCommand extends TwitchChatCommand, ResponseCommand<PrivMsgMessage> {
}
