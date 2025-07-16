package dataAccess;

import model.AuthData;

import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {

    HashSet<AuthData> db;

    public MemoryAuthDAO() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public void addAuth(String authToken, String username) {
        db.add(new AuthData(username, authToken));
    }

    @Override
    public void addAuth(AuthData authData) {
        db.add(authData);
    }

    @Override
    public void deleteAuth(String authToken) {
        for (AuthData authData : db) {
            if (authData.authToken().equals(authToken)) {
                db.remove(authData);
                break;
            }
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        for (AuthData authData : db) {
            if (authData.authToken().equals(authToken)) {
                return authData;
            }
        }
        throw new DataAccessException("Auth Token does not exist: " + authToken);
    }

    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public void insertAuth(String token, String username) {

    }

    @Override
    public void insertAuth(AuthData auth) {

    }

    @Override
    public void removeAuth(String token) {

    }

    @Override
    public AuthData fetchAuth(String token) throws DataAccessException {
        return null;
    }

    @Override
    public void resetStorage() {

    }
}