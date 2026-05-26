package service;

import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.util.UUID;

public class UserService {

    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    //L
    public UserService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }


    public AuthData register(String username, String password, String email) throws DataAccessException {
        try {
            UserData user = userDAO.getUser(username);
            String authToken = UUID.randomUUID().toString();
            if (username == null) {
                throw new DataAccessException("Error: bad request");
            } else if (password == null) {
                throw new DataAccessException("Error: bad request");
            } else if (username.isEmpty()) {
                throw new DataAccessException("Error: bad request");
            } else if (password.isEmpty()) {
                throw new DataAccessException("Error: bad request");
            } else if (user != null) {
                throw new DataAccessException("Error 403: username already taken");
            } else {
                userDAO.createUser(username, password, email);
                return authDAO.createAuth(authToken, username);
            }
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: internal error");
        }
    }

    public AuthData login(String username, String password) throws DataAccessException {
        try {
            UserData user = userDAO.getUser(username);
            String authToken = UUID.randomUUID().toString();
            if (username == null) {
                throw new DataAccessException("Error: bad request");
            } else if (password == null) {
                throw new DataAccessException("Error: bad request");
            } else if (username.isEmpty()) {
                throw new DataAccessException("Error: bad request");
            } else if (password.isEmpty()) {
                throw new DataAccessException("Error: bad request");
            } else if (user == null) {
                throw new DataAccessException("Error: unauthorized");
            } else if (user.password().startsWith("$2a$") || user.password().startsWith("$2b$") || user.password().startsWith("$2y$")) {
                if (BCrypt.checkpw(password, user.password())) {
                    return authDAO.createAuth(authToken, username);
                } else {
                    throw new DataAccessException("Error: unauthorized");
                }
            } else if (!user.password().equals(password)) {
                throw new DataAccessException("Error: unauthorized");
            } else {
                return authDAO.createAuth(authToken, username);
            }
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: internal error");
        }
    }

    public boolean logout(String authToken) throws DataAccessException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            if (auth == null) {
                throw new DataAccessException("Error: unauthorized");
            } else {
                authDAO.deleteAuth(authToken);
                return true;
            }
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: internal error");
        }
    }



}
