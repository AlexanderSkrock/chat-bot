package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.command.CommandContext;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TwitchCommandContext implements CommandContext {

    private final ChatBotUserInterface userInterface;
}
