package dataAccess;

import model.AuthData;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    private final Map<String, AuthData> authDatabase;

    public MemoryAuthDAO() {
        authDatabase = new HashMap<>();
    }

    @Override
    public void addAuth(AuthData authData) {
        authDatabase.put(authData.authToken(), authData);
    }

    @Override
    public void deleteAuth(String authToken) {
        authDatabase.remove(authToken);
    }

    @Override
    public AuthData getAuth(String authToken) throws UnauthorizedException {
        AuthData authData = authDatabase.get(authToken);
        if (authData == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }
        return authData;
    }

    @Override
    public void clear() {
        authDatabase.clear();
    }
}