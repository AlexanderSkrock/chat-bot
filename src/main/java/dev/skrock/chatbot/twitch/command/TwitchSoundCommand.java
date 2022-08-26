package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.SoundCommand;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;

public interface TwitchSoundCommand extends TwitchChatCommand, SoundCommand<PrivMsgMessage> {
}
