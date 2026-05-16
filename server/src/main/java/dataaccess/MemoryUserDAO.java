package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();

    UserData createUser(String username, String password, String email) {
        UserData newUser = new UserData(username, password, email);

        users.put(username, newUser);
        return newUser;
    }

    UserData getUser(String username) {
        return users.get(username);
    }

    void clear() {
        users.clear();
    }

}
