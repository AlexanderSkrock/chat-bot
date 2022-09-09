package dev.skrock.chatbot.core.discord;

import dev.skrock.chatbot.core.ChatBotExtension;
import dev.skrock.chatbot.core.ExtendableChatBot;
import dev.skrock.chatbot.core.Platform;
import dev.skrock.chatbot.core.SupportsCommands;
import dev.skrock.chatbot.discord.DiscordConfiguration;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.mesaging.DiscordEventHandler;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
public class DiscordChatBot extends ExtendableChatBot<DiscordChatBot> implements SupportsCommands<DiscordChatCommand> {

    protected final DiscordConfiguration configuration;

    protected final List<DiscordEventHandler<?>> eventHandlers = new LinkedList<>();

    protected DiscordClient client;
    protected GatewayDiscordClient gateway;

    protected boolean supportsCommands = false;
    protected final Set<DiscordChatCommand> commands = new HashSet<>();

    public DiscordChatBot(DiscordConfiguration configuration, Collection<ChatBotExtension<DiscordChatBot>> extensions) {
        super(extensions);
        this.configuration = configuration;
    }

    @Override
    public Platform getPlatform() {
        return Platform.DISCORD;
    }

    @Override
    public void reset() {
        this.client = null;
        this.gateway = null;
        this.eventHandlers.clear();
        this.supportsCommands = false;
        this.commands.clear();
    }

    @Override
    protected void internalInit() {
        this.client = DiscordClient.create(configuration.getToken());
        this.eventHandlers.add(new DiscordEventHandler<ReadyEvent>() {
            @Override
            public Class<ReadyEvent> getSupportedClass() {
                return ReadyEvent.class;
            }

            @Override
            public Mono<DiscordMessageOut> handleEvent(Event event) {
                ReadyEvent readyEvent = checkEvent(event);
                User user = readyEvent.getSelf();
                log.info("Successfully connected to discord servers as {}#{}!", user.getUsername(), user.getDiscriminator());
                return Mono.empty();
            }
        });
    }

    @Override
    protected void internalStart() {
        gateway = client.login().block();
        if (gateway == null) {
            throw new RuntimeException("Failed to connect to discord servers!");
        }
        gateway.onDisconnect().doOnTerminate(() -> log.info("Disconnected!"));
        eventHandlers.forEach(handler -> gateway.on(handler.getSupportedClass(), handler::handleEvent).subscribe());
    }

    @Override
    protected void internalStop() {
        if (gateway != null) {
            gateway.logout();
            gateway = null;
        }
    }

    public void addEventHandler(DiscordEventHandler<?> eventHandler) {
        eventHandlers.add(eventHandler);
    }

    @Override
    public boolean supportsCommands() {
        return supportsCommands;
    }

    @Override
    public void enableCommandSupport() {
        this.supportsCommands = true;
    }

    @Override
    public void disableCommandSupport() {
        this.supportsCommands = false;
    }

    @Override
    public void addCommand(DiscordChatCommand command) {
        commands.add(command);
    }

    @Override
    public Set<? extends DiscordChatCommand> getCommands() {
        return commands;
    }
}
