package dev.skrock.chatbot.discord;

import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import discord4j.core.event.domain.Event;
import org.reactivestreams.Publisher;

public interface DiscordEventHandler<E extends Event> {

    Class<E> getSupportedClass();

    default E checkEvent(Event event) {
        if (!getSupportedClass().isInstance(event)) {
            throw new IllegalArgumentException("event of type " + event.getClass() + " is not suported");
        }
        return (E) event;
    }

    Publisher<DiscordMessageOut> handleEvent(Event event);
}
