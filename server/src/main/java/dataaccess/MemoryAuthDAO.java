package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO {

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    AuthData createAuth(String authToken, String username) {
        AuthData authData = new AuthData(authToken, username);

        authTokens.put(authToken, authData);
        return authData;
    }

    AuthData getAuth(String authToken) {
        return authTokens.get(authToken);
    }

    void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    void clear() {
        authTokens.clear();
    }

}
