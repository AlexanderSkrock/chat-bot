package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.audio.PreloadedSound;
import dev.skrock.chatbot.audio.Sound;
import dev.skrock.chatbot.command.SupportsAudioPlayback;
import dev.skrock.chatbot.discord.command.DiscordChatCommand;
import dev.skrock.chatbot.discord.command.DiscordCommandContext;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageIn;
import dev.skrock.chatbot.discord.mesaging.DiscordMessageOut;
import dev.skrock.chatbot.storage.SoundEntity;
import org.apache.logging.log4j.util.Strings;
import reactor.core.publisher.Mono;

public class CustomDiscordCommand implements DiscordChatCommand {
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
    public boolean matches(DiscordMessageIn message) {
        String text = message.getMessageCreateEvent().getMessage().getContent();
        return Strings.isNotBlank(text) && text.startsWith(trigger);
    }

    @Override
    public Mono<DiscordMessageOut> execute(DiscordMessageIn message, DiscordCommandContext commandContext) {
        if (sound != null && commandContext instanceof SupportsAudioPlayback audioContext) {
            audioContext.getAudioPlayer().play(sound);
        }

        return message.getMessageCreateEvent().getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(response))
                .map(DiscordMessageOut::new);
    }
}
