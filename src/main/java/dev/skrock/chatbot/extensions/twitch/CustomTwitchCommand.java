package dev.skrock.chatbot.extensions.twitch;

import dev.skrock.chatbot.storage.Sound;
import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.twitch.command.TwitchCommandContext;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import reactor.core.publisher.Mono;

@Slf4j
public class CustomTwitchCommand implements TwitchChatCommand {
    private final String trigger;
    private final String response;
    private final Sound sound;

    public CustomTwitchCommand(String trigger, String response) {
        this(trigger, response, null);
    }

    public CustomTwitchCommand(String trigger, Sound sound) {
        this(trigger, null, sound);
    }

    public CustomTwitchCommand(String trigger, String response, Sound sound) {
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
    public Mono<PrivMsgMessage> execute(PrivMsgMessage message, TwitchCommandContext commandContext) {
        if (sound != null) {
            commandContext.getUserInterface().notifyUser(new SimpleChatBotUserNotification(sound));
        }
        return Mono.just(new PrivMsgMessage(message.getChannel(), response));
    }
}
