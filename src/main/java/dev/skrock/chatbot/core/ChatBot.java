package dev.skrock.chatbot.core;

public interface ChatBot extends HasPlatform {

    void reset();

    void initialize();

    void start();

    void stop();
}
