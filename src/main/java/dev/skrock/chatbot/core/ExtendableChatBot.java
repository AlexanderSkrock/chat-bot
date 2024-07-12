package dev.skrock.chatbot.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ExtendableChatBot<SELF extends ExtendableChatBot<SELF>> implements ChatBot {

    protected Collection<ChatBotExtension<SELF>> extensions = new LinkedList<>();

    protected ExtendableChatBot(Collection<ChatBotExtension<SELF>> extensions) {
        this.extensions.addAll(extensions);
    }

    @Override
    public void initialize() {
        getExtensions().forEach(extension -> extension.onBeforeInit(self()));
        internalInit();
        getExtensions().forEach(extension -> extension.onAfterInit(self()));
    }

    @Override
    public void start() {
        getExtensions().forEach(extension -> extension.onBeforeStart(self()));
        internalStart();
        getExtensions().forEach(extension -> extension.onAfterStart(self()));
    }

    @Override
    public void stop() {
        getExtensions().forEach(extension -> extension.onBeforeStop(self()));
        internalStop();
        getExtensions().forEach(extension -> extension.onAfterStop(self()));
    }

    protected Collection<ChatBotExtension<SELF>> getExtensions() {
        return extensions.stream().filter(extension -> Objects.equals(extension.getPlatform(), getPlatform())).collect(Collectors.toList());
    }

    private SELF self() {
        return (SELF) this;
    }

    protected abstract void internalInit();
    protected abstract void internalStart();
    protected abstract void internalStop();
}
