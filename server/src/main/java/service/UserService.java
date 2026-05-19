package service;

import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }


    public AuthData register(String username, String password, String email) throws DataAccessException {
        UserData user = userDAO.getUser(username);
        String authToken = UUID.randomUUID().toString();
        if (user != null) {
            throw new DataAccessException("Error 403: username already taken");
        } else {
            userDAO.createUser(username, password, email);
            return authDAO.createAuth(authToken, username);
        }
    }

    public AuthData login(String username, String password) throws DataAccessException {
        UserData user = userDAO.getUser(username);
        String authToken = UUID.randomUUID().toString();
        if (user == null) {
            throw new DataAccessException("Error: unauthorized");
        } else if (user.password().equals(password)) {
            throw new DataAccessException("Error: unauthorized");
        } else {
            return authDAO.createAuth(authToken, username);
        }
    }

    public boolean logout(String authToken) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new DataAccessException("Error: unauthroized");
        } else {
            authDAO.deleteAuth(authToken);
            return true;
        }
    }

}
