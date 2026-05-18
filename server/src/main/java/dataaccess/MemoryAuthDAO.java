package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO {

    final private HashMap<String, AuthData> authTokens = new HashMap<>();

    public AuthData createAuth(String authToken, String username) {
        AuthData authData = new AuthData(authToken, username);

        authTokens.put(authToken, authData);
        return authData;
    }

    public AuthData getAuth(String authToken) {
        return authTokens.get(authToken);
    }

    public void deleteAuth(String authToken) {
        authTokens.remove(authToken);
    }

    public void clear() {
        authTokens.clear();
    }

}
