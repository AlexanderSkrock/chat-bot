package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.command.Command;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordEventHandler;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class DiscordCommandMessageHandler implements DiscordEventHandler<MessageCreateEvent> {

    private final CommandExecutor<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> commandExecutor;
    private final Set<? extends Command<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut>> commands;

    public DiscordCommandMessageHandler(CommandExecutor<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut> commandExecutor, Set<? extends Command<DiscordCommandContext, DiscordMessageIn, DiscordMessageOut>> commands) {
        this.commandExecutor = commandExecutor;
        this.commands = commands;
    }

    @Override
    public Class<MessageCreateEvent> getSupportedClass() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<DiscordMessageOut> handleEvent(Event event) {
        MessageCreateEvent createEvent = checkEvent(event);

        boolean shouldSkipMessage = createEvent.getMessage().getAuthor().filter(Predicate.not(User::isBot)).isEmpty();
        if (shouldSkipMessage) {
            log.debug("Skipped message: {}", createEvent);
            return Mono.empty();
        }

        DiscordMessageIn message = new DiscordMessageIn(createEvent);
        log.debug("Received message: {}", message);

        Iterable<Mono<DiscordMessageOut>> commandExecutions = commands.stream()
                .filter(command -> command.matches(message))
                .map(command -> commandExecutor.execute(command, message))
                .collect(Collectors.toList());

        return Mono.firstWithValue(commandExecutions).map(response -> {
            log.debug("Sending response: {}", response);
            return response;
        });
    }
}
