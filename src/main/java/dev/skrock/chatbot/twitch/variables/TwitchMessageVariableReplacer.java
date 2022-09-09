package dev.skrock.chatbot.twitch.variables;

import dev.skrock.chatbot.twitch.messaging.TwitchMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        Optional<TwitchMessageVariable> twitchVariable = variables.stream().filter(variable -> variable.name().equals(variableName)).findFirst();

        twitchVariable.ifPresent(var -> log.debug("Replacing variable: {}", var));

        return twitchVariable.map(variable -> variable.value(message));
    }
}
