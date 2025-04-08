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
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if(path.equals("/api/players")) {
            List<Player> players = playerDAO.findAll();
            JSONArray response = JsonUtil.toJsonArray(players);
            sendResponse(exchange, 200, response.toString());
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
