package dev.skrock.chatbot.ui;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ServerSideUserInterfaceBridge implements ChatBotUserInterface {
    @Override
    public Mono<ChatBotUserNotificationResponse> notifyUser(ChatBotUserNotification notification) {
        notification.getMessage().ifPresent(log::info);
        notification.getSound().ifPresent(sound -> {
            try {
                playSound(sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                log.error("Fehler beim Abspielen des Sounds:", e);
            }
        });
        return Mono.empty();
    }

    protected void playSound(InputStream audioStream) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(audioStream));
        clip.start();
    }
}
