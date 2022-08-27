package dev.skrock.chatbot.discord;

import dev.skrock.chatbot.command.ActionCommand;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.command.ResponseCommand;
import dev.skrock.chatbot.command.SoundCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import dev.skrock.chatbot.storage.Sound;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

@Component
public class DiscordCommandExecutor implements CommandExecutor<DiscordMessage> {

    @Override
    public void executeActionCommand(ActionCommand<DiscordMessage> command, DiscordMessage message) {
        command.execute(message);
    }

    @Override
    public void executeSoundCommand(SoundCommand<DiscordMessage> command, DiscordMessage message) {
        Sound sound = command.getSound(message);
        if (sound == null) {
            return;
        }
        // TODO Implement audio playback logic
    }

    @Override
    public Publisher<DiscordMessage> executeResponseCommand(ResponseCommand<DiscordMessage> command, DiscordMessage message) {
        return command.getResponse(message);
    }
}
