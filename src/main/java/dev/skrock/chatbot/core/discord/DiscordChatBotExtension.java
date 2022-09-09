package dev.skrock.chatbot.core.discord;

import dev.skrock.chatbot.core.ChatBotExtension;
import dev.skrock.chatbot.core.Platform;

public interface DiscordChatBotExtension extends ChatBotExtension<DiscordChatBot> {

    @Override
    default Platform getPlatform() {
        return Platform.DISCORD;
    }
}
