package dev.skrock.chatbot.ui;

import dev.skrock.chatbot.util.SoundUtils;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ServerSideUserInterfaceBridge implements ChatBotUserInterface {
    @Override
    public CompletableFuture<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification) {
        notification.getMessage().ifPresent(log::info);
        notification.getSound().ifPresent(sound -> {
            try {
                SoundUtils.play(sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                log.error("Fehler beim Abspielen des Sounds:", e);
            }
        });

        // FIXME Future korrekt completen
        return CompletableFuture.completedFuture(null);
    }
}
