package dev.skrock.chatbot.twitch;

import dev.skrock.chatbot.command.ActionCommand;
import dev.skrock.chatbot.command.CommandExecutor;
import dev.skrock.chatbot.command.ResponseCommand;
import dev.skrock.chatbot.command.SoundCommand;
import dev.skrock.chatbot.storage.Sound;
import dev.skrock.chatbot.twitch.messaging.PrivMsgMessage;
import dev.skrock.chatbot.twitch.variables.TwitchMessageVariableReplacer;
import dev.skrock.chatbot.ui.ChatBotUserInterface;
import dev.skrock.chatbot.ui.SimpleChatBotUserNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwitchCommandExecutor implements CommandExecutor<PrivMsgMessage> {
    private final TwitchMessageVariableReplacer variableReplacer;
    private final ChatBotUserInterface userInterface;

    public TwitchCommandExecutor(TwitchMessageVariableReplacer variableReplacer, ChatBotUserInterface userInterface) {
        this.variableReplacer = variableReplacer;
        this.userInterface = userInterface;
    }

    @Override
    public void executeActionCommand(ActionCommand<PrivMsgMessage> command, PrivMsgMessage message) {
        command.execute(message);
    }

    @Override
    public void executeSoundCommand(SoundCommand<PrivMsgMessage> command, PrivMsgMessage message) {
        Sound sound = command.getSound(message);
        if (sound == null) {
            return;
        }
        userInterface.notifyUser(new SimpleChatBotUserNotification(sound));
    }

    @Override
    public PrivMsgMessage executeResponseCommand(ResponseCommand<PrivMsgMessage> command, PrivMsgMessage message) {
        PrivMsgMessage response = command.getResponse(message);
        response.setMessage(variableReplacer.replace(response.getMessage(), message));
        return response;
    }
}
