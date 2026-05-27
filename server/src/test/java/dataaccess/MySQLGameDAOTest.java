package dataaccess;

import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MySQLGameDAOTest {

    private MySQLAuthDAO mySQLAuthDAO;
    private MySQLUserDAO mySQLUserDAO;
    private MySQLGameDAO mySQLGameDAO;

    @BeforeEach
    void setup() throws DataAccessException {
        mySQLAuthDAO = new MySQLAuthDAO();
        mySQLUserDAO = new MySQLUserDAO();
        mySQLGameDAO = new MySQLGameDAO();

        mySQLUserDAO.clear();
        mySQLAuthDAO.clear();
        mySQLGameDAO.clear();

        mySQLUserDAO.createUser("butt", "butt", "butt@mail");
        mySQLGameDAO.createGame(null, null, "momma", new ChessGame());
    }

    @Test
    void createGame() throws DataAccessException {
        int yo = mySQLGameDAO.createGame(null, null, "yo", new ChessGame());
        assertNotNull(mySQLGameDAO.getGame(yo));
    }

    @Test
    void getGame() throws DataAccessException {
        int yo = mySQLGameDAO.createGame(null, null, "yo", new ChessGame());
        assertNotNull(mySQLGameDAO.getGame(yo));
    }

    @Test
    void listGames() throws DataAccessException {
        assertNotNull(mySQLGameDAO.listGames());
    }

    @Test
    void updateGame() throws DataAccessException {
        int yo = mySQLGameDAO.createGame(null, null, "yo", new ChessGame());
        mySQLGameDAO.updateGame(yo, null, "yo", "momma", mySQLGameDAO.getGame(yo).game());
        assertEquals("yo", mySQLGameDAO.getGame(yo).blackUsername());
    }

    @Test
    void negativeCreateGame() throws DataAccessException {
        assertThrows(DataAccessException.class, () ->  mySQLGameDAO.createGame(null, null, null, null));
    }

    @Test
    void negativeGetGame() throws DataAccessException {
        assertNull(mySQLGameDAO.getGame(0));
    }

    @Test
    void negativeListGames() throws DataAccessException {
        mySQLGameDAO.clear();
        assertEquals(new ArrayList<ChessGame>(), mySQLGameDAO.listGames());
    }

    @Test
    void negativeUpdateGame() throws DataAccessException {
        mySQLGameDAO.updateGame(0, null, null, null, new ChessGame());
        assertThrows(NullPointerException.class, () -> mySQLGameDAO.getGame(0).game());
    }

    @Test
    void clear() throws DataAccessException {
        mySQLGameDAO.clear();
        assertEquals(new ArrayList<ChessGame>(), mySQLGameDAO.listGames());
    }
}