package dev.skrock.chatbot.discord.command;

import dev.skrock.chatbot.discord.mesaging.DiscordMessage;
import dev.skrock.chatbot.storage.Sound;
import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Publisher;

public class CustomDiscordCommand implements DiscordResponseCommand, DiscordSoundCommand {
    private final String trigger;
    private final String response;
    private final Sound sound;

    public CustomDiscordCommand(String trigger, String response) {
        this(trigger, response, null);
    }

    public CustomDiscordCommand(String trigger, Sound sound) {
        this(trigger, null, sound);
    }

    public CustomDiscordCommand(String trigger, String response, Sound sound) {
        this.trigger = trigger;
        this.response = response;
        this.sound = sound;
    }

    @Override
    public boolean matches(DiscordMessage message) {
        String text = message.getMessage().getContent();
        return Strings.isNotBlank(text) && text.startsWith(trigger);
    }

    @Override
    public Publisher<DiscordMessage> getResponse(DiscordMessage message) {
        return message.getMessage().getChannel().flatMap(channel -> channel.createMessage(response)).map(DiscordMessage::new);
    }

    @Override
    public Sound getSound(DiscordMessage message) {
        return sound;
    }
}
