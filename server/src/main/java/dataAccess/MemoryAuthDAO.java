package dataAccess;

import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    private final Map<String, UserData> userDatabase;  // Key: username, Value: UserData

    public MemoryUserDAO() {
        userDatabase = new HashMap<>();
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        UserData user = userDatabase.get(username);
        if (user == null) {
            throw new DataAccessException("User not found: " + username);
        }
        return user;
    }

    @Override
    public void createUser(UserData user) throws AlreadyExistsException {
        if (user == null || user.username() == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        if (userDatabase.containsKey(user.username())) {
            throw new AlreadyExistsException("User already exists: " + user.username());
        }

        userDatabase.put(user.username(), user);
    }

    @Override
    public boolean authenticateUser(String username, String password) throws UnauthorizedException {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }

        UserData user = userDatabase.get(username);
        if (user == null) {
            throw new UnauthorizedException("User does not exist: " + username);
        }

        if (!user.password().equals(password)) {
            throw new UnauthorizedException("Incorrect password for user: " + username);
        }

        return true;
    }

    @Override
    public void clear() {
        userDatabase.clear();
    }
}