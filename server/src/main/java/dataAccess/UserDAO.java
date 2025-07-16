package dataAccess;

import model.UserData;

public interface UserDAO {

    UserData getUser(String username) throws DataAccessException;

    void createUser(String username, String password, String email) throws DataAccessException;

    void createUser(UserData user) throws DataAccessException;

    boolean authenticateUser(String username, String password) throws DataAccessException;

    void clear();

    UserData findUserByUsername(String username);

    void addUser(String username, String password, String email);

    boolean validateCredentials(String username, String password);

    void reset();
}
