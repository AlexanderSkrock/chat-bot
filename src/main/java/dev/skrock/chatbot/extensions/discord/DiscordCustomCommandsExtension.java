package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.audio.sources.SoundTrack;
import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.discord.DiscordChatBotExtension;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.storage.CustomizableCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DiscordCustomCommandsExtension implements DiscordChatBotExtension {

    private final CustomizableCommandRepository commandRepository;

    @Autowired
    public DiscordCustomCommandsExtension(CustomizableCommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public void onBeforeInit(DiscordChatBot bot) {

    }

    @Override
    public void onAfterInit(DiscordChatBot bot) {
        if (!bot.supportsCommands()) {
            log.info("The bot does not support commands. Hence, this extension will be disabled.");
        }
        getCustomCommands().forEach(bot::addCommand);
    }

    @Override
    public void onBeforeStart(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStart(DiscordChatBot bot) {

    }

    @Override
    public void onBeforeStop(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStop(DiscordChatBot bot) {

    }

    protected Set<DiscordChatCommand> getCustomCommands() {
        return commandRepository
                .findAll()
                .stream()
                .map(commandFromRepo -> new CustomDiscordCommand(
                        commandFromRepo.getTrigger(),
                        commandFromRepo.getResponse(),
                        Optional.ofNullable(commandFromRepo.getSound()).map(SoundTrack::new).orElse(null)
                ))
                .collect(Collectors.toSet());
    }
}
