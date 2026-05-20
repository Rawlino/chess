package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private MemoryAuthDAO memoryAuthDAO;
    private MemoryUserDAO memoryUserDAO;
    private UserService userService;

    @BeforeEach
    void setup() throws DataAccessException {
        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();
        userService = new UserService(memoryAuthDAO, memoryUserDAO);
    }

    @Test
    void registerPositive() throws DataAccessException {
        AuthData auth = userService.register("butt", "butt", "butt@mail");

        assertEquals("butt@mail", memoryUserDAO.getUser("butt").email());
    }

    @Test
    void registerNegative() throws DataAccessException {
        userService.register("butt", "butt", "butt@mail");

        assertThrows(DataAccessException.class, () -> userService.register("butt", "yourmom", "failbutt@mail"));
    }

    @Test
    void loginPositive() throws DataAccessException {
        AuthData auth = userService.register("butt", "butt", "butt@mail");

        assertEquals(auth, memoryAuthDAO.getAuth(auth.authToken()));
    }

    @Test
    void loginNegative() throws DataAccessException {
        AuthData auth = userService.register("butt", "butt", "butt@mail");

        assertThrows(DataAccessException.class, () -> userService.login("butt", "definitelyNotWrongPassword"));
    }

    @Test
    void logoutPositive() throws DataAccessException {
    }

    @Test
    void logoutNegative() throws DataAccessException {
    }
}