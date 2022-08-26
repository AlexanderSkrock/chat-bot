package dev.skrock.chatbot.util;

import dev.skrock.chatbot.storage.Sound;
import lombok.experimental.UtilityClass;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class SoundUtils {

    public void play(InputStream soundStream) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(soundStream));
        clip.start();
    }

    public void play(Sound sound) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        ByteArrayInputStream soundStream = new ByteArrayInputStream(sound.getData());
        play(soundStream);
    }
}
