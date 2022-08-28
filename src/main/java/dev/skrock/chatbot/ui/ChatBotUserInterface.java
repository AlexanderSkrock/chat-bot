package dev.skrock.chatbot.ui;

import org.reactivestreams.Publisher;

public interface ChatBotUserInterface {

    Publisher<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification);
}
