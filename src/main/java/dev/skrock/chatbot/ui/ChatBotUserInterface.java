package dev.skrock.chatbot.ui;

import reactor.core.publisher.Mono;

public interface ChatBotUserInterface {

    Mono<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification);
}
