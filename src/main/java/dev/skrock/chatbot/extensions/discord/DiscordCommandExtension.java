package dev.skrock.chatbot.extensions.discord;

import dev.skrock.chatbot.core.discord.DiscordChatBot;
import dev.skrock.chatbot.core.discord.DiscordChatBotExtension;
import dev.skrock.chatbot.discord.command.DiscordCommandExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DiscordCommandExtension implements DiscordChatBotExtension {

    private final DiscordCommandExecutor commandExecutor;

    @Autowired
    public DiscordCommandExtension(DiscordCommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void onBeforeInit(DiscordChatBot bot) {
        if (bot.supportsCommands()) {
            log.info("The bot already supports commands. Hence, this extension will be disabled");
            return;
        }
        bot.enableCommandSupport();
        bot.addEventHandler(new DiscordCommandMessageHandler(commandExecutor, bot.getCommands()));
    }

    @Override
    public void onAfterInit(DiscordChatBot bot) {

    }

    @Override
    public void onBeforeStart(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStart(DiscordChatBot bot) {

    }

    @Override
    public void onBeforeStop(DiscordChatBot bot) {

    }

    @Override
    public void onAfterStop(DiscordChatBot bot) {

    }
}
