package dev.skrock.chatbot.twitch.messaging;

import dev.skrock.chatbot.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class TwitchMessage implements Message {
    private Tags tags;
    private Source source;
    private TwitchCommands command;
    private TwitchIRCCommandParameters parameters;
    private String message;

    public static class Tags extends HashMap<String, Object> {
    }

    @Data
    public static class Source {
        private String userName;
        private String hostName;
    }

    public static class TwitchIRCCommandParameters extends ArrayList<String> {
        public static TwitchIRCCommandParameters of(String... parameters) {
            TwitchIRCCommandParameters cmdParameters = new TwitchIRCCommandParameters();
            if (parameters != null && parameters.length > 0) {
                cmdParameters.addAll(Arrays.asList(parameters));
            }
            return cmdParameters;
        }
    }
}
