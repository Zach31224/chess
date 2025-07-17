package dataaccess;

import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    private final Map<String, UserData> userDatabase = new HashMap<>(); // key: username

    public UserData getUser(String username) throws DataAccessException {
        UserData user = userDatabase.get(username);
        if (user == null) {
            throw new DataAccessException("User not found");
        }
        return user;
    }

    public void createUser(UserData user) throws DataAccessException {
        if (userDatabase.containsKey(user.username())) {
            throw new DataAccessException("Error: already taken");
        }
        userDatabase.put(user.username(), user);
    }

    public boolean authenticateUser(String username, String password) throws DataAccessException {
        UserData user = getUser(username);
        return user.password().equals(password);
    }

    public void clear() {
        userDatabase.clear();
    }
}