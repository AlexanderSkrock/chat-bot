package dev.skrock.chatbot.discord;

import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DiscordCommandMessageHandler implements DiscordEventHandler<MessageCreateEvent> {

    private final DiscordCommandExecutor commandExecutor;
    private final List<DiscordChatCommand> commands;

    @Autowired
    public DiscordCommandMessageHandler(DiscordCommandExecutor commandExecutor, List<DiscordChatCommand> commands) {
        this.commandExecutor = commandExecutor;
        this.commands = new LinkedList<>(commands);
    }

    @Override
    public Class<MessageCreateEvent> getSupportedClass() {
        return MessageCreateEvent.class;
    }

    @Override
    public Publisher<DiscordMessage> handleEvent(Event event) {
        MessageCreateEvent createEvent = checkEvent(event);

        boolean shouldSkipMessage = createEvent.getMessage().getAuthor().filter(Predicate.not(User::isBot)).isEmpty();
        if (shouldSkipMessage) {
            return Mono.empty();
        }

        DiscordMessage message = new DiscordMessage(createEvent.getMessage());
        Iterable<Publisher<DiscordMessage>> responsePublishers = commands
                .stream()
                .filter(command -> command.matches(message))
                .map(command -> commandExecutor.execute(command, message))
                .collect(Collectors.toList());

        return Flux.mergeSequential(responsePublishers);
    }
}
