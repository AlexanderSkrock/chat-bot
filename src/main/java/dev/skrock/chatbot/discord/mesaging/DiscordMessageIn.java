package dev.skrock.chatbot.discord.mesaging;

import dev.skrock.chatbot.messaging.Message;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscordMessageIn implements Message {

    private MessageCreateEvent messageCreateEvent;
}
