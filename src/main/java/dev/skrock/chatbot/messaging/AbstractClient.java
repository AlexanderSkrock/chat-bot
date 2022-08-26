package dev.skrock.chatbot.messaging;

import reactor.core.scheduler.Schedulers;

public abstract class AbstractClient<Raw, M extends Message, C extends Connection<Raw>> implements Client<M> {

    protected C connection;

    private final MessageInConverter<Raw, M> inMessageConverter;
    private final MessageOutConverter<Raw, M> outMessageConverter;

    public AbstractClient(C connection, MessageInConverter<Raw, M> inMessageConverter, MessageOutConverter<Raw, M> outMessageConverter) {
        this.connection = connection;
        this.inMessageConverter = inMessageConverter;
        this.outMessageConverter = outMessageConverter;
    }

    public void init() {
        if (!connection.isConnected()) {
            connection.connect();

            connection.getMessagesFlux().subscribeOn(Schedulers.parallel()).subscribe(rawMessage -> {
                M ircMessage = inMessageConverter.convert(rawMessage);
                handleMessage(ircMessage);
            });

            authorize();
        }
    }

    public abstract void handleMessage(M message);

    public void sendMessage(M message) {
        Raw rawMessage = outMessageConverter.convert(message);
        connection.sendMessage(rawMessage);
    }
}
