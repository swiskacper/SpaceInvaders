package Config;

import Domain.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnector {

    private final AppConfiguration appConfiguration = new AppConfiguration();

    private final String url = appConfiguration.getProperties().getProperty("postgresURL");
    private final String user = appConfiguration.getProperties().getProperty("postgresUser");
    private final String password = appConfiguration.getProperties().getProperty("postgresPassword");

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> scores = new ArrayList<>();

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public long insertPlayer(Player player) {

        String sqlQuote = "INSERT INTO  \"public\".\"Player\"(name,id,score) values (?,?,?);";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuote,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(2, player.getId());
            pstmt.setString(1, player.getName());
            pstmt.setString(3, player.getScore());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public void getPlayers() {
        String sql = "Select * from \"public\".\"Player\"";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            getPlayersData(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void getPlayersData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            names.add(rs.getString("name"));
            ids.add(rs.getString("id"));
            scores.add(rs.getString("score"));
        }
        System.out.println(names);
        System.out.println(ids);
        System.out.println(scores);
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<String> getScores() {
        return scores;
    }
}

