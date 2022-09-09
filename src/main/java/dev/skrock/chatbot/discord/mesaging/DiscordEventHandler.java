package dev.skrock.chatbot.discord.mesaging;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Mono;

public interface DiscordEventHandler<E extends Event> {

    Class<E> getSupportedClass();

    default E checkEvent(Event event) {
        if (!getSupportedClass().isInstance(event)) {
            throw new IllegalArgumentException("event of type " + event.getClass() + " is not suported");
        }
        return (E) event;
    }

    Mono<DiscordMessageOut> handleEvent(Event event);
}
