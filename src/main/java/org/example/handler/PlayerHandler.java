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
