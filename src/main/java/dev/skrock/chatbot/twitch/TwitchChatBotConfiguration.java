package dev.skrock.chatbot.twitch;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("twitch")
@EnableConfigurationProperties
public class TwitchChatBotConfiguration {

    private String username;
    private String token;
    private String channel;
}
