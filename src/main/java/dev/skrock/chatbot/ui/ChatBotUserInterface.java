package dev.skrock.chatbot.ui;

import java.util.concurrent.CompletableFuture;

public interface ChatBotUserInterface {

    CompletableFuture<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification);
}
