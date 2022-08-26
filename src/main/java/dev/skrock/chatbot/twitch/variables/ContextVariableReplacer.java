package dev.skrock.chatbot.twitch.variables;

public interface ContextVariableReplacer<Context> {
    String replace(String input, Context context);
}
