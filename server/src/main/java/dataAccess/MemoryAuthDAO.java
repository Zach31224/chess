package dataAccess;

import model.AuthData;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    private final Map<String, AuthData> authDatabase;  // Key: authToken, Value: AuthData

    public MemoryAuthDAO() {
        authDatabase = new HashMap<>();
    }

    @Override
    public void addAuth(AuthData authData) {
        if (authData == null || authData.authToken() == null) {
            throw new IllegalArgumentException("Auth data cannot be null");
        }
        authDatabase.put(authData.authToken(), authData);
    }

    @Override
    public void deleteAuth(String authToken) {
        if (authToken == null) {
            throw new IllegalArgumentException("Auth token cannot be null");
        }
        authDatabase.remove(authToken);
    }

    @Override
    public AuthData getAuth(String authToken) throws UnauthorizedException {
        if (authToken == null) {
            throw new IllegalArgumentException("Auth token cannot be null");
        }

        AuthData authData = authDatabase.get(authToken);
        if (authData == null) {
            throw new UnauthorizedException("Auth token not found: " + authToken);
        }
        return authData;
    }

    @Override
    public void clear() {
        authDatabase.clear();
    }
}