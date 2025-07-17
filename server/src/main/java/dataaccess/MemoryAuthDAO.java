package dataaccess;

import model.AuthData;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    private final Map<String, AuthData> authDatabase = new HashMap<>();

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(username, authToken);
        authDatabase.put(authToken, authData);
        return authData;
    }

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {
        authDatabase.put(authData.authToken(), authData);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (!authDatabase.containsKey(authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }
        authDatabase.remove(authToken);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        AuthData authData = authDatabase.get(authToken);
        if (authData == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return authData;
    }

    @Override
    public void clear() throws DataAccessException {
        authDatabase.clear();
    }
}