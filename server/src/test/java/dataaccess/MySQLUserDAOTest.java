package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MySQLUserDAOTest {

    private MySQLAuthDAO mySQLAuthDAO;
    private MySQLUserDAO mySQLUserDAO;

    @BeforeEach
    void setup() throws DataAccessException {
        mySQLAuthDAO = new MySQLAuthDAO();
        mySQLUserDAO = new MySQLUserDAO();

        mySQLUserDAO.clear();
        mySQLAuthDAO.clear();

        mySQLUserDAO.createUser("butt", "butt", "butt@mail");
    }

    @Test
    void createUser() throws DataAccessException {
        assertEquals("butt@mail", mySQLUserDAO.getUser("butt").email());
    }

    @Test
    void getUser() throws DataAccessException {
        assertEquals("butt", mySQLUserDAO.getUser("butt").username());
    }

    @Test
    void negativeCreateUser() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> mySQLUserDAO.createUser("butt", "l", "l"));
    }

    @Test
    void negativeGetUser() throws DataAccessException {
        assertNull(mySQLUserDAO.getUser("but"));
    }

    @Test
    void clear() throws DataAccessException {
        mySQLUserDAO.clear();
        assertNull(mySQLUserDAO.getUser("butt"));
    }
}