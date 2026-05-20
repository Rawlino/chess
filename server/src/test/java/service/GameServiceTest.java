package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private MemoryGameDAO memoryGameDAO;
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryUserDAO memoryUserDAO;
    private GameService gameService;
    private UserService userService;

    @BeforeEach
    void setup() throws DataAccessException {
        memoryGameDAO = new MemoryGameDAO();
        memoryAuthDAO = new MemoryAuthDAO();
        memoryUserDAO = new MemoryUserDAO();
        gameService = new GameService(memoryGameDAO, memoryAuthDAO);
        userService = new UserService(memoryAuthDAO, memoryUserDAO);

        userService.register("butt", "butt", "butt@mail");
    }

    @Test
    void listGamesPositive() throws DataAccessException {
        AuthData auth = userService.login("butt", "butt");

        gameService.createGame(auth.authToken(), "Test");

        Collection<GameData> list = gameService.listGames(auth.authToken());

        assertNotNull(list);
    }

    @Test
    void listGamesNegative() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> gameService.listGames("fakeauth"));
    }

    @Test
    void createGamePositive() throws DataAccessException {
        AuthData auth = userService.login("butt", "butt");

        int iD = gameService.createGame(auth.authToken(), "Test");

        assertEquals("Test", memoryGameDAO.getGame(iD).gameName());
    }

    @Test
    void createGameNegative() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> gameService.createGame(null, "Test"));
    }

    @Test
    void joinGamePositive() throws DataAccessException {
        AuthData auth = userService.login("butt", "butt");

        int id = gameService.createGame(auth.authToken(), "Test");

        boolean result = gameService.joinGame(auth.authToken(), "WHITE", id);

        assertTrue(result);
    }

    @Test
    void joinGameNegative() throws DataAccessException {
        AuthData auth = userService.login("butt", "butt");

        int id = gameService.createGame(auth.authToken(), "Test");

        gameService.joinGame(auth.authToken(), "WHITE", id);

        AuthData twinAuth = userService.register("buttEvilTwin", "butt", "buttTwin@mail");

        assertThrows(DataAccessException.class, () -> gameService.joinGame(twinAuth.authToken(), "WHITE", id));
    }
}