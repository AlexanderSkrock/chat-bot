package dev.skrock.chatbot;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import dev.skrock.chatbot.audio.sources.AudioLoader;
import dev.skrock.chatbot.audio.sources.AudioSource;
import dev.skrock.chatbot.audio.sources.SoundRepositorySource;
import dev.skrock.chatbot.audio.sources.YoutubeSource;
import dev.skrock.chatbot.storage.SoundRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public YoutubeSource youtubeSource(YouTube youTube) {
        return new YoutubeSource(youTube);
    }

    @Bean
    public YouTube youTube(@Value("${youtube.token}") String youtubeToken) {
        // TODO use proper configuration object instead of plain @Value
        YouTube.Builder builder = new YouTube.Builder(
                new NetHttpTransport(),
                new GsonFactory(),
                httpRequest -> {}
        );
        builder.setApplicationName("Atsinatlai");
        builder.setGoogleClientRequestInitializer(new YouTubeRequestInitializer(youtubeToken));

        return builder.build();
    }
}
