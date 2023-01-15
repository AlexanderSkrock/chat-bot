package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.audio.AudioPlayer;
import dev.skrock.chatbot.command.CommandContext;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.twitch.music.TwitchAudioPlayer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TwitchCommandContext implements CommandContext {

    private final ChatBotUserInterface userInterface;
}
