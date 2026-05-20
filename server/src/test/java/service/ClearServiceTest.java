package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    @Test
    void clearDBPositive() throws DataAccessException {
        MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
        MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        ClearService clearService = new ClearService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
        UserService userService = new UserService(memoryAuthDAO, memoryUserDAO);

        userService.register("butt", "butt", "butt@mail");

        clearService.clearDB();

        assertNull(memoryUserDAO.getUser("butt"));
    }

}