package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.storage.CustomizableCommandRepository;
import dev.skrock.chatbot.twitch.command.CustomTwitchCommand;
import dev.skrock.chatbot.twitch.command.EchoCommand;
import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Configuration
@ConfigurationProperties("twitch")
@EnableConfigurationProperties
public class TwitchChatBotConfiguration {

    private String username;
    private String token;
    private String channel;

    @Bean
    public List<TwitchChatCommand> twitchCommands(CustomizableCommandRepository commandRepository) {
        LinkedList<TwitchChatCommand> commands = new LinkedList<>();
        commands.addAll(systemCommands());
        commands.addAll(customCommands(commandRepository));
        return commands;
    }

    private List<TwitchChatCommand> systemCommands() {
        return List.of(new EchoCommand());
    }

    private List<TwitchChatCommand> customCommands(CustomizableCommandRepository commandRepository) {
        return commandRepository
                .findAll()
                .stream()
                .map(commandFromRepo -> new CustomTwitchCommand(commandFromRepo.getTrigger(), commandFromRepo.getResponse(), commandFromRepo.getSound()))
                .collect(Collectors.toList());
    }
}
