package dev.skrock.chatbot.command;

import dev.skrock.chatbot.messaging.Message;
import dev.skrock.chatbot.storage.Sound;

public interface SoundCommand<M extends Message> extends Command<M> {

    Sound getSound(M message);
}
