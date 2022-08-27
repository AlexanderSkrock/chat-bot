package dev.skrock.chatbot.discord.mesaging;

import dev.skrock.chatbot.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordMessage implements Message {

    private discord4j.core.object.entity.Message message;
}
