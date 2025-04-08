package org.example.server;

import com.sun.net.httpserver.HttpServer;
import org.example.dao.PlayerDAO;
import org.example.handler.PlayerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    private HttpServer server;
    private final int port;

    public SimpleHttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/players", new PlayerHandler(new PlayerDAO()));

        server.setExecutor(null);
        server.start();
    }
}
