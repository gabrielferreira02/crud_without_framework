package org.example.dao;

import org.example.config.DatabaseConfig;
import org.example.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public List<Player> findAll() {
        String sql = "SELECT * FROM players";
        List<Player> players = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                Player player = new Player();
                player.setId(rs.getLong("id"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setTeam(rs.getString("team"));
                players.add(player);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return  players;
    }

    public Player findById(long id) {
        String sql = "SELECT * FROM players WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                Player player = new Player();
                player.setId(rs.getLong("id"));
                player.setName(rs.getString("name"));
                player.setPosition(rs.getString("position"));
                player.setTeam(rs.getString("team"));
                return player;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM players WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
