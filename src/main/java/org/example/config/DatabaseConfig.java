package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.configure().load();
    private static final String BASE_URL = dotenv.get("BASE_URL");
    private static final String DB_NAME = "crudplayersdb";
    private static final String USER = dotenv.get("USER");
    private static final String PASS = dotenv.get("PASSWORD");

    public static void initDatabase() {
        createDatabaseIfNotExists();
        createTables();
    }

    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(BASE_URL + "postgres", USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = '" + DB_NAME + "'");

            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                System.out.println("Banco de dados criado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar banco: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void createTables() {
        String sql;
        try {
            sql = new String(Files.readAllBytes(
                    Paths.get("src/main/resources/init.sql")));
        } catch (IOException e) {
            throw new RuntimeException("Arquivo init.sql não encontrado", e);
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Tabelas criadas com sucesso!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(BASE_URL + DB_NAME, USER, PASS);
    }
}
