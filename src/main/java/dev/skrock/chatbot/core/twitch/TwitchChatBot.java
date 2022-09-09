package dev.skrock.chatbot.core.twitch;

import dev.skrock.chatbot.core.ChatBotExtension;
import dev.skrock.chatbot.core.ExtendableChatBot;
import dev.skrock.chatbot.core.Platform;
import dev.skrock.chatbot.core.SupportsCommands;
import dev.skrock.chatbot.twitch.TwitchChatBotConfiguration;
import dev.skrock.chatbot.twitch.command.TwitchChatCommand;
import dev.skrock.chatbot.twitch.messaging.irc.TwitchIRCClient;
import dev.skrock.chatbot.twitch.messaging.irc.TwitchMessageHandler;

import java.util.*;

public class TwitchChatBot extends ExtendableChatBot<TwitchChatBot> implements SupportsCommands<TwitchChatCommand> {

    protected final TwitchChatBotConfiguration configuration;

    protected TwitchIRCClient twitchClient;
    protected final List<TwitchMessageHandler> messageHandlers = new LinkedList<>();

    protected boolean supportsCommands = false;
    protected final Set<TwitchChatCommand> commands = new HashSet<>();

    public TwitchChatBot(TwitchChatBotConfiguration configuration, Collection<ChatBotExtension<TwitchChatBot>> chatBotExtensions) {
        super(chatBotExtensions);
        this.configuration = configuration;
    }

    @Override
    public Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public void reset() {
        this.twitchClient = null;
        this.messageHandlers.clear();
        this.supportsCommands = false;
        this.commands.clear();
    }

    @Override
    protected void internalInit() {
        this.twitchClient = new TwitchIRCClient(configuration.getUsername(), configuration.getToken(), messageHandlers);
    }

    @Override
    protected void internalStart() {
        this.twitchClient.connect();
        this.twitchClient.joinChannel(configuration.getChannel());
    }

    @Override
    protected void internalStop() {
        this.twitchClient.disconnect();
    }

    public void addMessageHandler(TwitchMessageHandler messageHandler) {
        this.messageHandlers.add(messageHandler);
    }

    @Override
    public boolean supportsCommands() {
        return supportsCommands;
    }

    @Override
    public void enableCommandSupport() {
        this.supportsCommands = true;
    }

    @Override
    public void disableCommandSupport() {
        this.supportsCommands = false;
    }

    @Override
    public void addCommand(TwitchChatCommand command) {
        this.commands.add(command);
    }

    @Override
    public Set<? extends TwitchChatCommand> getCommands() {
        return commands;
    }
}
