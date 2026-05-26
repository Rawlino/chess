package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MySQLAuthDAOTest {

    private MySQLAuthDAO mySQLAuthDAO;
    private MySQLUserDAO mySQLUserDAO;
    private String authToken = UUID.randomUUID().toString();

    @BeforeEach
    void setup() throws DataAccessException {
        mySQLAuthDAO = new MySQLAuthDAO();
        mySQLUserDAO = new MySQLUserDAO();

        mySQLUserDAO.clear();
        mySQLAuthDAO.clear();

        mySQLUserDAO.createUser("butt", "butt", "butt@mail");
    }

    @Test
    void createAuth() throws DataAccessException {
        AuthData auth = mySQLAuthDAO.createAuth(authToken, "butt");
        assertEquals(auth, mySQLAuthDAO.getAuth(authToken));
    }

    @Test
    void getAuth() throws DataAccessException {
        AuthData auth = mySQLAuthDAO.createAuth(authToken, "butt");
        assertNotNull(mySQLAuthDAO.getAuth(auth.authToken()));
    }

    @Test
    void deleteAuth() throws DataAccessException {
        AuthData auth = mySQLAuthDAO.createAuth(authToken, "butt");
        mySQLAuthDAO.deleteAuth(auth.authToken());
        assertNotEquals(authToken, mySQLAuthDAO.getAuth(auth.authToken()));
    }

    @Test
    void negativeCreateAuth() throws DataAccessException {
        assertThrows(DataAccessException.class , () -> mySQLAuthDAO.createAuth(authToken, null));
    }

    @Test
    void negativeGetAuth() throws DataAccessException {
        assertNull(mySQLAuthDAO.getAuth("NullNull"));
    }

    @Test
    void negativeDeleteAuth() throws DataAccessException {
        AuthData auth = mySQLAuthDAO.createAuth(authToken, "butt");
        mySQLAuthDAO.deleteAuth("wawa");
        assertNotNull(auth.authToken());
    }

    @Test
    void positiveClear() throws DataAccessException {
        AuthData auth = mySQLAuthDAO.createAuth(authToken, "butt");
        mySQLAuthDAO.clear();
        assertNull(mySQLAuthDAO.getAuth(authToken));
    }
}