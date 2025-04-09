package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.dao.PlayerDAO;
import org.example.model.Player;
import org.example.util.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlayerHandler implements HttpHandler {
    private final PlayerDAO playerDAO;

    public PlayerHandler(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("GET".equals(exchange.getRequestMethod())) {
            handleGetRequest(exchange);
        }

        if("DELETE".equals(exchange.getRequestMethod())) {
            handleDeleteRequest(exchange);
        }

        if("POST".equals(exchange.getRequestMethod())) {
            handlePostRequest(exchange);
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if(path.equals("/api/players")) {
            List<Player> players = playerDAO.findAll();
            JSONArray response = JsonUtil.toJsonArray(players);
            sendResponse(exchange, 200, response.toString());
            return;
        }

        if(path.startsWith("/api/players/")) {
            String[] pathParts = exchange.getRequestURI().getPath().split("/");
            long id = Long.parseLong(pathParts[pathParts.length - 1]);

            Player player  = playerDAO.findById(id);

            if(player != null) {
                JSONObject response = JsonUtil.toJson(player);
                sendResponse(exchange, 200, response.toString());
            } else {
                sendResponse(exchange, 404, "{\"error\":\"Jogador não encontrado\"}");
            }
        }
    }

    private void handlePostRequest(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();

        if(path.equals("/api/players")) {
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

                JSONObject jsonBody = new JSONObject(body);

                Player player = new Player();
                player.setName(jsonBody.getString("name"));
                player.setPosition(jsonBody.getString("position"));
                player.setTeam(jsonBody.getString("team"));

                if(player.getName().isBlank()) {
                    sendResponse(exchange, 404, "{\"error\":\"Nome não pode ser vazio\"}");
                    return;
                }

                if(player.getPosition().isBlank()) {
                    sendResponse(exchange, 404, "{\"error\":\"Posição não pode ser vazio\"}");
                    return;
                }

                if(player.getTeam().isBlank()) {
                    sendResponse(exchange, 404, "{\"error\":\"Time não pode ser vazio\"}");
                    return;
                }

                if(playerDAO.save(player)) {
                    sendResponse(exchange, 201, "{\"message\":\"Jogador criado com sucesso\"}");
                    return;
                }

                sendResponse(exchange, 404, "{\"error\":\"Falha ao criar jogador\"}");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if(path.startsWith("/api/players/")) {
            String[] pathParts = exchange.getRequestURI().getPath().split("/");
            long id = Long.parseLong(pathParts[pathParts.length - 1]);

            if(playerDAO.deleteById(id)) {
                sendNoContentResponse(exchange);
                return;
            }

            sendResponse(exchange, 404, "{\"error\":\"Jogador id não encontrado\"}");
        }
    }


    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendNoContentResponse(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(204, -1);
        exchange.getResponseBody().close();
    }
}
