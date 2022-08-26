package dev.skrock.chatbot.twitch.variables;

public interface ContextualVariable<Context> {
    String name();
    String value(Context context);
}
