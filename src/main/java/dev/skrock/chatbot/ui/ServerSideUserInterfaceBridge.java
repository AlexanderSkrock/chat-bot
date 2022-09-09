package dev.skrock.chatbot.ui;

import dev.skrock.chatbot.util.SoundUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@Slf4j
public class ServerSideUserInterfaceBridge implements ChatBotUserInterface {
    @Override
    public Mono<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification) {
        notification.getMessage().ifPresent(log::info);
        notification.getSound().ifPresent(sound -> {
            try {
                SoundUtils.play(sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                log.error("Fehler beim Abspielen des Sounds:", e);
            }
        });
        return Mono.empty();
    }
}
