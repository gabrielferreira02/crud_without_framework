package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.config.DatabaseConfig;
import org.example.server.SimpleHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig.initDatabase();
        startHttpServer();
    }

    private static void startHttpServer() {
        try {
            SimpleHttpServer server = new SimpleHttpServer(8080);
            server.start();
            System.out.println("🌐 Servidor HTTP rodando na porta 8080");
        } catch (IOException e) {
            System.err.println("❌ Falha ao iniciar servidor HTTP: " + e.getMessage());
            System.exit(1);
        }
    }
}