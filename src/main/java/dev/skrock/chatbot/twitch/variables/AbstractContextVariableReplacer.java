package dev.skrock.chatbot.twitch.variables;

import org.apache.commons.text.StringSubstitutor;

import java.util.Optional;

public abstract class AbstractContextVariableReplacer<Context> implements ContextVariableReplacer<Context> {

    @Override
    public String replace(String input, Context context) {
        StringSubstitutor substitutor = new StringSubstitutor(
                var -> replaceVariable(var, context).orElse(null),
                getVariablePrefix(),
                getVariableSuffix(),
                StringSubstitutor.DEFAULT_ESCAPE
        );
        return substitutor.replace(input);
    }

    protected abstract String getVariablePrefix();
    protected abstract String getVariableSuffix();

    protected abstract Optional<String> replaceVariable(String variableName, Context context);
}
