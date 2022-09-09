package dev.skrock.chatbot.core.twitch;

import dev.skrock.chatbot.core.ChatBotExtension;
import dev.skrock.chatbot.core.Platform;

public interface TwitchChatBotExtension extends ChatBotExtension<TwitchChatBot> {

    @Override
    default Platform getPlatform() {
        return Platform.TWITCH;
    }
}
