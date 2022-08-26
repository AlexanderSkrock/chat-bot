package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.ActionCommand;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface TwitchActionCommand extends TwitchChatCommand, ActionCommand<PrivMsgMessage> {
}
