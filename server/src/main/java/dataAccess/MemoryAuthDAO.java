package dataAccess;

import model.AuthData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {

    private HashSet<AuthData> database;

    public MemoryAuthDAO() {
        database = HashSet.newHashSet(16);
    }

    @Override
    public void addAuth(AuthData authData) {
        database.add(authData);
    }

    @Override
    public void deleteAuth(String authToken) {
        AuthData toRemove = null;
        for (AuthData data : database) {
            if (data.authToken().equals(authToken)) {
                toRemove = data;
                break;
            }
        }
        if (toRemove != null) {
            database.remove(toRemove);
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData data : database) {
            if (data.authToken().equals(authToken)) {
                return data;
            }
        }
        throw new DataAccessException("Auth token not found: " + authToken);
    }

    @Override
    public void clear() {
        database = HashSet.newHashSet(16);
    }
}
