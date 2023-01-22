package dev.skrock.chatbot.extensions.twitch;

import dev.skrock.chatbot.audio.sources.SoundTrack;
import dev.skrock.chatbot.core.twitch.TwitchChatBot;
import dev.skrock.chatbot.core.twitch.TwitchChatBotExtension;
import dev.skrock.chatbot.storage.CustomizableCommandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TwitchCustomCommandsExtension implements TwitchChatBotExtension {

    private final CustomizableCommandRepository commandRepository;

    @Autowired
    public TwitchCustomCommandsExtension(CustomizableCommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public void onBeforeInit(TwitchChatBot bot) {

    }

    @Override
    public void onAfterInit(TwitchChatBot bot) {
        if (!bot.supportsCommands()) {
            log.info("The bot does not support commands. Hence, this extension will be disabled.");
        }
        getCustomCommands().forEach(bot::addCommand);
    }

    @Override
    public void onBeforeStart(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStart(TwitchChatBot bot) {

    }

    @Override
    public void onBeforeStop(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStop(TwitchChatBot bot) {

    }

    protected Set<CustomTwitchCommand> getCustomCommands() {
        return commandRepository
                .findAll()
                .stream()
                .map(commandFromRepo -> new CustomTwitchCommand(
                        commandFromRepo.getTrigger(),
                        commandFromRepo.getResponse(),
                        Optional.ofNullable(commandFromRepo.getSound()).map(SoundTrack::new).orElse(null)
                ))
                .collect(Collectors.toSet());
    }
}
