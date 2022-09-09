package dev.skrock.chatbot.messaging.irc;

import dev.skrock.chatbot.messaging.Connection;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class IRCConnection implements Connection<String> {
    private String host;
    private int port;

    private Socket sock;
    private PrintWriter out;
    private Flux<String> messages;

    public IRCConnection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void connect() {
        try {
            this.sock = new Socket(this.host, this.port);
            BufferedReader readerIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            this.messages = Flux.fromStream(readerIn.lines());
            this.out = new PrintWriter(sock.getOutputStream(), true);

            log.info("Successfully connected to the server");
        } catch (IOException e) {
            log.error(e.getMessage());
            this.sock = null;
        }
    }

    @Override
    public void disconnect() {
        try {
            sock.close();
        } catch (IOException e) {
            log.error("Fehler beim Schließen der Verbindung", e);
        } finally {
            sock = null;
        }
    }

    @Override
    public boolean isConnected() {
        return sock != null && sock.isConnected();
    }

    @Override
    public Flux<String> getMessagesFlux() {
        return this.messages;
    }

    @Override
    public void sendMessage(String message) {
        this.out.println(message);
    }
}
