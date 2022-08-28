package dev.skrock.chatbot.discord;

import dev.skrock.chatbot.discord.command.CustomDiscordCommand;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.command.EchoCommand;
import dev.skrock.chatbot.discord.command.SongRequestCommand;
import dev.skrock.chatbot.storage.CustomizableCommandRepository;
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
@ConfigurationProperties("discord")
@EnableConfigurationProperties
public class DiscordChatBotConfiguration {

    private String token;

    @Bean
    public List<DiscordChatCommand> discordCommands(CustomizableCommandRepository commandRepository) {
        LinkedList<DiscordChatCommand> commands = new LinkedList<>();
        commands.addAll(systemCommands());
        commands.addAll(customCommands(commandRepository));
        return commands;
    }

    private List<DiscordChatCommand> systemCommands() {
        return List.of(
                new EchoCommand(),
                new SongRequestCommand()
        );
    }

    private List<DiscordChatCommand> customCommands(CustomizableCommandRepository commandRepository) {
        return commandRepository
                .findAll()
                .stream()
                .map(commandFromRepo -> new CustomDiscordCommand(commandFromRepo.getTrigger(), commandFromRepo.getResponse(), commandFromRepo.getSound()))
                .collect(Collectors.toList());
    }
}
