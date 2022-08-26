package dev.skrock.chatbot.twitch.command;

import dev.skrock.chatbot.storage.Sound;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
public class CustomCommand implements TwitchResponseCommand, TwitchSoundCommand {
    private final String trigger;
    private final String response;
    private final Sound sound;

    public CustomCommand(String trigger, String response) {
        this(trigger, response, null);
    }

    public CustomCommand(String trigger, Sound sound) {
        this(trigger, null, sound);
    }

    public CustomCommand(String trigger, String response, Sound sound) {
        this.trigger = trigger;
        this.response = response;
        this.sound = sound;
    }

    @Override
    public boolean matches(PrivMsgMessage message) {
        String text = message.getMessage();
        return Strings.isNotBlank(text) && text.startsWith(trigger);
    }

    @Override
    public PrivMsgMessage getResponse(PrivMsgMessage message) {
        return new PrivMsgMessage(message.getChannel(), response);
    }

    @Override
    public Sound getSound(PrivMsgMessage message) {
        return sound;
    }
}
