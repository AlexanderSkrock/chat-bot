package dev.skrock.chatbot.ui.vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.ChatBotUserNotification;
import dev.skrock.chatbot.ui.ChatBotUserNotificationResponse;
import dev.skrock.chatbot.ui.vaadin.components.AudioPlayer;
import dev.skrock.chatbot.util.StreamResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeType;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class VaadinUserInterfaceBridge implements ChatBotUserInterface {

    private UI vaadinUi;

    public void setUI(UI vaadinUi) {
        this.vaadinUi = vaadinUi;
    }

    @Override
    public Mono<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification userNotification) {
        CompletableFuture<ChatBotUserNotificationResponse> future = new CompletableFuture<>();

        vaadinUi.accessSynchronously(() -> {
            Notification notification = new Notification();
            userNotification.getMessage().ifPresent(notification::setText);
            userNotification.getSound().ifPresent(sound -> {
                StreamResource resource = StreamResourceUtils.getStreamResource(UUID.randomUUID().toString(), new MimeType("audio", "mpeg"), sound);

                AudioPlayer notificationSoundPlayer = new AudioPlayer();
                notificationSoundPlayer.enableControls();
                notificationSoundPlayer.enableAutoplay();
                notificationSoundPlayer.onFinished(evt -> {
                    notification.close();
                    future.complete(null);
                });
                notificationSoundPlayer.setSource(resource);

                notification.add(notificationSoundPlayer);
                notification.addDetachListener(evt -> {
                    notificationSoundPlayer.pause();
                    future.complete(null);
                });
            });
            notification.open();
        });

        return Mono.fromFuture(future);
    }
}