package dataaccess;

import model.AuthData;

public interface AuthDAO {

    AuthData createAuth(String username) throws DataAccessException;

    void addAuth(AuthData authData) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void clear() throws DataAccessException;
}