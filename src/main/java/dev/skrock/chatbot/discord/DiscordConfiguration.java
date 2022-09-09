package dev.skrock.chatbot.discord;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("discord")
@EnableConfigurationProperties
public class DiscordConfiguration {

    private String token;
}
