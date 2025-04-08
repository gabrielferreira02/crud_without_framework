package org.example.util;

import org.example.model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JsonUtil {
    public static JSONObject toJson(Player player) {
        return new JSONObject()
                .put("id", player.getId())
                .put("name", player.getName())
                .put("position", player.getPosition())
                .put("team", player.getTeam());
    }

    public static JSONArray toJsonArray(List<Player> players) {
        JSONArray array = new JSONArray();
        for (Player p: players) {
            array.put(toJson(p));
        }
        return array;
    }
}
