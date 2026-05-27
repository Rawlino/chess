package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static dataaccess.MySQLUserDAO.*;
import static dataaccess.DatabaseManager.createDatabase;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    public int createGame(String whiteUsername, String blackUsername, String gameName, ChessGame game)
            throws DataAccessException {
        try {
            var statement = "INSERT INTO games (whiteUsername, blackUsername, gameName, game)" +
                    " VALUES (?, ?, ?, ?)";
            String readableGame = new Gson().toJson(game);
            int gameID = executeUpdate(statement, whiteUsername, blackUsername, gameName, readableGame);
            return gameID;
        } catch (DataAccessException e) {
            extracted("Error: internal error");
            return 0;
        }
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try {
            try (Connection conn = DatabaseManager.getConnection()) {
                GameData rs = getGameData(gameID, conn);
                if (rs != null) {
                    return rs;
                }
            } catch (Exception e) {
                extracted(String.format("Unable to read data: %s", e.getMessage()));
            }
            return null;
        } catch (DataAccessException e) {
            extracted("Error: internal error");
            return null;
        }
    }

    @Nullable
    private GameData getGameData(int gameID, Connection conn) throws SQLException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM games WHERE gameID=?";
        try (PreparedStatement ps = conn.prepareStatement(statement)) {
            ps.setInt(1, gameID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readGame(rs);
                }
            }
        }
        return null;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        try {
            var result = new ArrayList<GameData>();
            try (Connection conn = DatabaseManager.getConnection()) {
                extractedSQL(conn, result);
            } catch (Exception e) {
                extracted(String.format("Unable to read data: %s", e.getMessage()));
                return null;
            }
            return result;
        } catch (DataAccessException e) {
            extracted("Error: internal error");
            return null;
        }
    }

    private void extractedSQL(Connection conn, ArrayList<GameData> result) throws SQLException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM games";
        try (PreparedStatement ps = conn.prepareStatement(statement)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(readGame(rs));
                }
            }
        }
    }

    public void updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game)
            throws DataAccessException {
        try {
            try {
                var statement = "UPDATE games SET whiteUsername=?, blackUsername=?, gameName=?, game=?" +
                        " WHERE gameID=?";
                String readableGame = new Gson().toJson(game);
                executeUpdate(statement, whiteUsername, blackUsername, gameName, readableGame, gameID);
            } catch (Exception e) {
                extracted(String.format("Unable to update data: %s", e.getMessage()));
            }
        } catch (DataAccessException e) {
            extracted("Error: internal error");
        }
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var game = rs.getString("game");
        ChessGame chessGame = new Gson().fromJson(game, ChessGame.class);
        GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame);
        return gameData;
    }

    private final String[] gameCreateStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` VARCHAR(255) DEFAULT NULL,
              `blackUsername` VARCHAR(255) DEFAULT NULL,
              `gameName` VARCHAR(255) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : gameCreateStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            extracted(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

}
