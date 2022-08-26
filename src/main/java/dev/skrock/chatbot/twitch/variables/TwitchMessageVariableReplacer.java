package dev.skrock.chatbot.twitch.variables;

import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TwitchMessageVariableReplacer extends AbstractContextVariableReplacer<TwitchMessage> {

    private final List<TwitchMessageVariable> variables;

    public TwitchMessageVariableReplacer(List<TwitchMessageVariable> variables) {
        this.variables = variables;
    }

    @Override
    protected String getVariablePrefix() {
        return "${";
    }

    @Override
    protected String getVariableSuffix() {
        return "}";
    }

    @Override
    protected Optional<String> replaceVariable(String variableName, TwitchMessage message) {
        return variables.stream()
                .filter(variable -> variable.name().equals(variableName))
                .findFirst()
                .map(variable -> variable.value(message));
    }
}
