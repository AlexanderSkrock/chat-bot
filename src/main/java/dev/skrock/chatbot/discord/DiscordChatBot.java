package dev.skrock.chatbot.discord;

import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DiscordChatBot {

    private final DiscordClient client;

    private final List<DiscordEventHandler<?>> eventHandlers;

    @Autowired
    public DiscordChatBot(DiscordChatBotConfiguration configuration, List<DiscordEventHandler<?>> eventHandlers) {
        this.client = DiscordClient.create(configuration.getToken());
        this.eventHandlers = new LinkedList<>(eventHandlers);
    }

    @PostConstruct
    public void init() {
        client.withGateway(gateway -> {
            registerSystemHooks(gateway);

            Iterable<Publisher<DiscordMessage>> publishers = eventHandlers
                    .stream()
                    .map(handler -> gateway.on(handler.getSupportedClass(), handler::handleEvent))
                    .collect(Collectors.toList());

            return Flux.mergeSequential(publishers);
        }).block();
    }

    private void registerSystemHooks(GatewayDiscordClient gateway) {
        gateway.on(ReadyEvent.class, event -> Mono.fromRunnable(() -> {
            User user = event.getSelf();
            log.info("Successfully connected to discord servers as {}#{}!", user.getUsername(), user.getDiscriminator());
        }));
        gateway.onDisconnect().doOnTerminate(() -> log.info("Disconnected!"));
    }
}
