package dev.skrock.chatbot.core;

public interface ChatBotLifeCycleAware<C extends ChatBot> {

    void onBeforeInit(C bot);
    void onAfterInit(C bot);

    void onBeforeStart(C bot);
    void onAfterStart(C bot);

    void onBeforeStop(C bot);
    void onAfterStop(C bot);
}
