package dev.skrock.chatbot.extensions.twitch;

import dev.skrock.chatbot.core.twitch.TwitchChatBot;
import dev.skrock.chatbot.core.twitch.TwitchChatBotExtension;
import dev.skrock.chatbot.twitch.command.TwitchCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TwitchCommandExtension implements TwitchChatBotExtension {

    private final TwitchCommandExecutor commandExecutor;

    @Autowired
    public TwitchCommandExtension(TwitchCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void onBeforeInit(TwitchChatBot bot) {
        if (bot.supportsCommands()) {
            log.info("The bot already supports commands. Hence, this extension will be disabled");
            return;
        }
        bot.enableCommandSupport();
        bot.addMessageHandler(new TwitchCommandMessageHandler(commandExecutor, bot.getCommands()));
    }

    @Override
    public void onAfterInit(TwitchChatBot bot) {

    }

    @Override
    public void onBeforeStart(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStart(TwitchChatBot bot) {

    }

    @Override
    public void onBeforeStop(TwitchChatBot bot) {

    }

    @Override
    public void onAfterStop(TwitchChatBot bot) {

    }
}
