package dev.skrock.chatbot.core;

import java.util.*;
import java.util.stream.Collectors;

public class ExtendableCrossPlatformChatBot extends ExtendableChatBot<ExtendableCrossPlatformChatBot> implements CrossPlatformChatBot {

    protected final Collection<ExtendableChatBot> chatBots = new LinkedList<>();

    public ExtendableCrossPlatformChatBot(Collection<ExtendableChatBot> chatBots, Collection<ChatBotExtension<ExtendableCrossPlatformChatBot>> extensions) {
        super(extensions);
        this.chatBots.addAll(chatBots);
    }

    @Override
    public void reset() {
        chatBots.forEach(ChatBot::reset);
    }

    @Override
    protected void internalInit() {
        chatBots.forEach(ChatBot::initialize);
    }

    @Override
    protected void internalStart() {
        chatBots.forEach(ChatBot::start);
    }

    @Override
    protected void internalStop() {
        chatBots.forEach(ChatBot::stop);
    }

    @Override
    public Set<Platform> getSupportedPlatforms() {
        return chatBots.stream().map(HasPlatform::getPlatform).collect(Collectors.toSet());
    }

    @Override
    public Optional<ChatBot> getChatBotForPlatform(Platform platform) {
        return chatBots.stream().filter(bot -> Objects.equals(bot.getPlatform(), platform)).findFirst().map(ChatBot.class::cast);
    }
}
