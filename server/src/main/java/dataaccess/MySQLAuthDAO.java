package dataaccess;

import java.sql.Connection;
import model.*;

import java.sql.*;

import static dataaccess.DatabaseManager.createDatabase;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static dataaccess.MySQLUserDAO.*;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    public AuthData createAuth(String authToken, String username) throws DataAccessException {
        try {
            var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
            executeUpdate(statement, authToken, username);
            return new AuthData(authToken, username);
        } catch (DataAccessException e) {
            extracted("Error: internal error");
            return null;
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try {
            try (Connection conn = DatabaseManager.getConnection()) {
                var statement = "SELECT authToken, username FROM auth WHERE authToken=?";
                try (PreparedStatement ps = conn.prepareStatement(statement)) {
                    ps.setString(1, authToken);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return readAuth(rs);
                        }
                    }
                }
            } catch (Exception e) {
                extracted(String.format("Unable to read data: %s", e.getMessage()));
                return null;
            }
            return null;
        } catch (DataAccessException e) {
            extracted("Error: internal error");
            return null;
        }
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authToken");
        var username = rs.getString("username");
        AuthData authData = new AuthData(authToken, username);
        return authData;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        try {
            var statement = "DELETE FROM auth WHERE authToken=?";
            executeUpdate(statement, authToken);
        } catch (DataAccessException e) {
            extracted("Error: internal error");
        }
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    private final String[] authCreateStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` VARCHAR(255) NOT NULL,
              `username` VARCHAR(255) NOT NULL,
              PRIMARY KEY (`authToken`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws DataAccessException {
        createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : authCreateStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            extracted(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

}
