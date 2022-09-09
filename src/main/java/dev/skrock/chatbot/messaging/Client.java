package dev.skrock.chatbot.messaging;

public interface Client<M extends Message> {
    boolean isConnect();
    void connect();
    void disconnect();

    boolean isAuthorized();
    void authorize();
    void resetAuthorization();

    void handleMessage(M message);
    void sendMessage(M message);
}
