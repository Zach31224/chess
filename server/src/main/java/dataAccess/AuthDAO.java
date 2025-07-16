package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void addAuth(String authToken, String username);

    void addAuth(AuthData authData);

    void deleteAuth(String authToken);

    AuthData getAuth(String authToken) throws DataAccessException;

    void clear();

    void insertAuth(String token, String username);

    void insertAuth(AuthData auth);

    void removeAuth(String token);

    AuthData fetchAuth(String token) throws DataAccessException;

    void resetStorage();
}
