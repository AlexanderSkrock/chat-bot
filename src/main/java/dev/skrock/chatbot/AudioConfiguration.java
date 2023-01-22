package dev.skrock.chatbot;

import dev.skrock.chatbot.audio.sources.AudioLoader;
import dev.skrock.chatbot.audio.sources.AudioSource;
import dev.skrock.chatbot.audio.sources.SoundRepositorySource;
import dev.skrock.chatbot.storage.SoundRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class AudioConfiguration {

    @Bean
    public AudioLoader audioLoader(List<AudioSource> sources) {
        return new AudioLoader(sources);
    }

    @Bean
    public AudioSource soundRepositorySource(SoundRepository soundRepository) {
        return new SoundRepositorySource(soundRepository);
    }
}
